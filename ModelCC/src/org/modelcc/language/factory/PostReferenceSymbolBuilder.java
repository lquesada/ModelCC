/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelcc.language.factory.ElementId;
import org.modelcc.language.syntax.PostSymbolBuilder;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.RuleElementPosition;
import org.modelcc.metamodel.ChoiceModelElement;
import org.modelcc.metamodel.ElementMember;
import org.modelcc.metamodel.MultipleElementMember;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.fence.Symbol;
import org.modelcc.tools.FieldFinder;

/**
 * Reference symbol builder
 * @author elezeta
 * @serial
 */
public final class PostReferenceSymbolBuilder extends PostSymbolBuilder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * The model.
     */
    private Model m;
    
    /**
     * Constructor
     * @param m the model.
     */
    public PostReferenceSymbolBuilder(Model m) {
        this.m = m;
    }
    
    /**
     * Builds a symbol, filling its listData, and validates it.
     * @param t symbol to be built.
     * @param data the parser listData.
     * @param usedIn used in map.
     * @return true if the symbol is valid, false if not
     */
    @Override
    public boolean build(Symbol t,Object data,Map<Symbol,Set<Symbol>> usedIn) {
        Map<Class,Map<KeyWrapper,Object>> ids = ((ModelCCParserData)data).getIds();
        Map<Object,ObjectWrapper> map = ((ModelCCParserData)data).getMap();
        Set<Symbol> lazyReferences = ((ModelCCParserData)data).getLazyReferences();
        
        if (!lazyReferences.contains(t))
            return true;
        
        ElementId eid = (ElementId)t.getType();
        ComplexModelElement ce = (ComplexModelElement) eid.getElement();
        Class c = ce.getElementClass();
        Object o;
        boolean valid = true;

        try {
            o = c.newInstance();
            Set<RuleElement> proc = new HashSet<RuleElement>();
            Set<Field> filled = new HashSet<Field>();
            for (int i = 0;i < t.getElements().size();i++) {
                Symbol s = t.getContents().get(i);
                RuleElement re = t.getElements().get(i);
                proc.add(re);
                if (re.getClass().equals(RuleElementPosition.class)) {
                    ElementMember ct = (ElementMember)((RuleElementPosition)re).getPositionId();
                    Field fld = FieldFinder.findField(c,ct.getField());
                    filled.add(fld);
                    Object list;
                    Method addm;
                    int j;
                    Object[] listData;
                    if (fld != null) {
                        fld.setAccessible(true);
                        if (!ct.getClass().equals(MultipleElementMember.class)) {
                            fld.set(o,s.getUserData());
                        }
                        else {
                            MultipleElementMember mc = (MultipleElementMember)ct;
                            ListContents listContents = (ListContents) s.getUserData();
                            listData = listContents.getL();
                            Symbol extra = listContents.getExtra();
                            RuleElementPosition extraRe = listContents.getExtraRuleElement();
                            if (extraRe != null) {
	                            ElementMember extraCt = (ElementMember)extraRe.getPositionId();
	                            Field extraFld = FieldFinder.findField(c,extraCt.getField());
	                            filled.add(extraFld);
	                            if (extraFld != null) {
	                            	extraFld.setAccessible(true);
	                            	extraFld.set(o,extra.getUserData());
	                            }
                            }
                            if (mc.getMinimumMultiplicity() != -1) {
                                if (listData.length<mc.getMinimumMultiplicity())
                                    valid = false;
                            }
                            if (mc.getMaximumMultiplicity() != -1) {
                                if (listData.length>mc.getMaximumMultiplicity())
                                    valid = false;
                            }
                            switch (mc.getCollection()) {
                                case LIST:
                                    if (fld.getType().isInterface())
                                            list = ArrayList.class.newInstance();
                                        else
                                            list = fld.getType().newInstance();
                                        addm = list.getClass().getDeclaredMethod("add",Object.class);
                                        if (listData != null)
                                            for (j = 0;j < listData.length;j++)
                                                addm.invoke(list,listData[j]);
                                        fld.set(o,list);
                                        break;
                                    case LANGARRAY:
                                        list = Array.newInstance(ct.getElementClass(),listData.length);
                                        if (listData != null)
                                            for (j = 0;j < listData.length;j++)
                                                Array.set(list, j, listData[j]);
                                        fld.set(o,list);
                                        break;
                                    case SET:
                                        if (fld.getType().isInterface())
                                            list = HashSet.class.newInstance();
                                        else
                                            list = fld.getType().newInstance();
                                        addm = list.getClass().getDeclaredMethod("add",Object.class);
                                        if (listData != null)
                                            for (j = 0;j < listData.length;j++)
                                                addm.invoke(list,listData[j]);
                                        fld.set(o,list);
                                        break;
                                }
                        }
                    }
                }
            }
            //fixOptionals(o,m,filled);
            /*if (ce.getAutorunMethod() != null) {
                Method mtd = c.getDeclaredMethod(ce.getAutorunMethod(),new Class[]{});
                if (mtd != null) {
                    mtd.setAccessible(true);
                    if (valid) {
                        if (mtd.getReturnType().equals(boolean.class) || mtd.getReturnType().equals(Boolean.class)) {
                            valid = (Boolean)mtd.invoke(o);
                        }
                        else {
                            mtd.invoke(o);
                            valid = true;
                        }
                    }
                }
            }*/
            //t.setUserData(o);

            if (ce.getIds().isEmpty())
                valid = false;
            if (valid) {
                Map<KeyWrapper,Object> idmap = ids.get(c);
                if (idmap == null) {
                    idmap = new HashMap<KeyWrapper,Object>();
                    ids.put(c,idmap);
                }
                KeyWrapper kw = KeyWrapper.getKeyWrapper(o, m,map);
                //DEBSystem.out.println("Fixing object of type "+c);
                if (idmap.containsKey(kw)) {
                    t.setUserData(idmap.get(kw));
                    //DEBSystem.out.println("Found! "+c+ " is "+idmap.get(kw));
                    if (usedIn.get(t) != null) {
                        for (Iterator<Symbol> ite = usedIn.get(t).iterator();ite.hasNext();) {
                            Symbol sym = ite.next();
                            //DEBSystem.out.println("Propagate to "+sym.getType());
                            
                            propagateChanges(sym,t,usedIn,0);
                            //DEBSystem.out.println("END PROP");
                            //DEBSystem.out.println("");
                            //DEBSystem.out.println("");
                        }
                    }
                }
                else {
                    valid = false;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CompositeSymbolBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return valid;
    }

    private void propagateChanges(Symbol t, Symbol updated, Map<Symbol, Set<Symbol>> usedIn,int array) throws IllegalArgumentException, IllegalAccessException, SecurityException, NoSuchFieldException {
        if (t.equals(updated))
            return;
        Object tData = t.getUserData();
        if (tData.getClass().equals(ListContents.class)) {
        	tData = ((ListContents)tData).getL();
        }
        Object uData = updated.getUserData();
        if (uData.getClass().equals(ListContents.class)) {
        	uData = ((ListContents)uData).getL();
        }

        if (((ElementId)(t.getRule().getLeft().getType())).getElement().getClass().equals(ChoiceModelElement.class)) {
            t.setUserData(uData);
            if (usedIn.get(t) != null) {
                for (Iterator<Symbol> ite = usedIn.get(t).iterator();ite.hasNext();) {
                    propagateChanges(ite.next(),t,usedIn,0);
                }
            }
            return;
        }
        
            //DEBSystem.out.println("EUREKA");
        //DEBSystem.out.println("------");
        //DEBSystem.out.println("Propagating symbols "+uData+" to "+tData);
        //DEBSystem.out.println("Rule of t is "+t.getRule());
        //DEBSystem.out.println("t Class is "+m.getClassToElement().get(tData.getClass()));
        //DEBSystem.out.println("Updated Class is "+m.getClassToElement().get(uData.getClass()));
        //DEBSystem.out.println();
        //DEBSystem.out.println("tData is "+tData);
        //DEBSystem.out.println("uData is "+uData);
        if (tData.getClass().isArray() && !uData.getClass().isArray()) {
            //DEBSystem.out.println("CASE 1, tData is array and uData is not array");
            Object[] atData = (Object[])tData;
            //DEBSystem.out.println("tData size is "+atData.length);
            if (!uData.equals(atData[0])) {
                atData[0] = uData;
                //DEBSystem.out.println("Pop!");
                if (usedIn.get(t) != null) {
                    for (Iterator<Symbol> ite = usedIn.get(t).iterator();ite.hasNext();) {
                        propagateChanges(ite.next(),t,usedIn,array+1);
                    }
                }
            }
        }
        else if (tData.getClass().isArray() && uData.getClass().isArray()) {
            //DEBSystem.out.println("CASE 2, tData is array and uData is array "+array);
            Object[] atData = (Object[])tData;
            Object[] auData = (Object[])uData;
            //DEBSystem.out.println("tData size is "+atData.length);
            //DEBfor (int i = 0;i < atData.length;i++) {
            //DEB    //DEBSystem.out.println("  contains "+i+" :"+atData[i]);
            //DEB}
            //DEBSystem.out.println("uData size is "+auData.length);
            //DEBfor (int i = 0;i < auData.length;i++) {
            //DEB    //DEBSystem.out.println("  contains "+i+" :"+auData[i]);
            //DEB}
            if (array<atData.length) {
	            if (!auData[0].equals(atData[array])) {
	                //DEBSystem.out.println("Pop!");
	                atData[array] = auData[array-1];
	                //DEBfor (int i = 0;i < atData.length;i++) {
	                //DEB    //DEBSystem.out.println("  NOW contains "+i+" :"+atData[i]);
	                //DEB}
	                if (usedIn.get(t) != null) {
	                    for (Iterator<Symbol> ite = usedIn.get(t).iterator();ite.hasNext();) {
	                        propagateChanges(ite.next(),t,usedIn,array+1);
	                    }
	                }
	            }
            }
        }
        else if (!tData.getClass().isArray() && uData.getClass().isArray()) {
            //DEBSystem.out.println("CASE 3, tData is not array and uData is array "+array);
            int index = t.getContents().indexOf(updated);
            RuleElement re = t.getElements().get(index);
            ElementMember ct = (ElementMember)((RuleElementPosition)re).getPositionId();
            Field fld = FieldFinder.findField(tData.getClass(),ct.getField());
            Object content = fld.get(tData);
            Object[] auData = (Object[])uData;
            Object[] aContent = (Object[])content;
            if (!auData[array-1].equals(aContent[array-1])) {
                //DEBSystem.out.println("Pop!");
                aContent[array-1] = auData[array-1];
                for (int i = 0;i < aContent.length;i++) {
                    //DEBSystem.out.println("  content contains "+i+" :"+aContent[i]);
                    if (usedIn.get(t) != null) {
                        for (Iterator<Symbol> ite = usedIn.get(t).iterator();ite.hasNext();) {
                            propagateChanges(ite.next(),t,usedIn,0);
                        }
                    }
                }
            }
            
            //DEBfor (int i = 0;i < aContent.length;i++) {
                //DEBSystem.out.println("  content NOW contains "+i+" :"+aContent[i]);
            //DEB}
        }
        else {
            //DEBSystem.out.println("CASE 4, tData is not array and uData is not array");
            int index = t.getContents().indexOf(updated);
            RuleElement re = t.getElements().get(index);
            ElementMember ct = (ElementMember)((RuleElementPosition)re).getPositionId();
            Field fld = FieldFinder.findField(tData.getClass(),ct.getField());
            fld.setAccessible(true);
            Object content = fld.get(tData);
            if (!uData.equals(content)) {
                //DEBSystem.out.println("Pop!");
                fld.set(tData,uData);
                if (usedIn.get(t) != null) {
                    for (Iterator<Symbol> ite = usedIn.get(t).iterator();ite.hasNext();) {
                        propagateChanges(ite.next(),t,usedIn,0);
                    }
                }
            }
        }
        //DEBSystem.out.println("END");
    }
        /*        Symbol parent = ite.next();      
        int index = parent.getContents().indexOf(t);
        //DEBSystem.out.println("The parent is "+parent.getUserData()+" "+parent.getType());
        //DEBSystem.out.println("In parent, it is: "+index+" which is "+parent.getElements().get(index).getType());
        //DEBSystem.out.println("The listData is of type: "+t.getUserData());
    }
*/

}
