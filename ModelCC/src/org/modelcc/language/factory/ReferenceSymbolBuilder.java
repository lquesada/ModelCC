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
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.RuleElementPosition;
import org.modelcc.language.syntax.SymbolBuilder;
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
public final class ReferenceSymbolBuilder extends SymbolBuilder implements Serializable {

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
    public ReferenceSymbolBuilder(Model m) {
        this.m = m;
    }
    
    /**
     * Builds a symbol, filling its listData, and validates it.
     * @param t symbol to be built.
     * @param data the parser listData.
     * @return true if the symbol is valid, false if not
     */
    @Override
	public boolean build(Symbol t,Object data,Map<Object, Set<Object>> emptyRules) {
        Map<Class,Map<KeyWrapper,Object>> ids = ((ModelCCParserData)data).getIds();
        Map<Object,ObjectWrapper> map = ((ModelCCParserData)data).getMap();
        Set<Symbol> lazyReferences = ((ModelCCParserData)data).getLazyReferences();

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

        } catch (Exception ex) {
            Logger.getLogger(CompositeSymbolBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        if (ce.getIds().isEmpty())
            valid = false;
        if (valid) {
            Map<KeyWrapper,Object> idmap = ids.get(c);
            if (idmap == null) {
                idmap = new HashMap<KeyWrapper,Object>();
                ids.put(c,idmap);
            }
            KeyWrapper kw = KeyWrapper.getKeyWrapper(o, m,map);
            if (idmap.containsKey(kw)) {
                t.setUserData(idmap.get(kw));
            }
            else {
                lazyReferences.add(t);
            }
        }
        return valid;
    }
}
