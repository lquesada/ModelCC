/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.Position;

import org.modelcc.language.LanguageSpecification;
import org.modelcc.language.lexis.LexicalSpecification;
import org.modelcc.language.lexis.TokenSpecification;
import org.modelcc.language.lexis.LexicalSpecificationFactory;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.language.syntax.SyntacticSpecification;
import org.modelcc.language.syntax.AssociativityConstraint;
import org.modelcc.language.syntax.SyntacticSpecificationFactory;
import org.modelcc.language.syntax.Rule;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.SymbolBuilder;
import org.modelcc.metamodel.*;
import org.modelcc.AssociativityType;
import org.modelcc.CompositionType;
import org.modelcc.language.syntax.PostSymbolBuilder;
import org.modelcc.language.syntax.RuleElementPosition;

/**
 * Language Specification Factory.
 * @author elezeta
 * @serial
 */
public final class LanguageSpecificationFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;


    /**
     * Decorator symbol builder
     */
    private static SymbolBuilder dsb = new DecoratorSymbolBuilder();

    /**
     * List symbol builder
     */
    private static SymbolBuilder lsb = new ListSymbolBuilder();

    /**
     * List element symbol builder
     */
    private static SymbolBuilder lesb = new ListElementSymbolBuilder();

    /**
     * List any symbol builder
     */
    private static SymbolBuilder lasb = new ListAnySymbolBuilder();

    /**
     * List zero symbol builder
     */
    private static SymbolBuilder lzsb = new ListZeroSymbolBuilder();

    /**
     * Converts a model into a language specification
     * @param m the model
     * @return the language specification
     * @throws CannotGenerateLanguageSpecificationException
     */
    public LanguageSpecification create(Model m) throws CannotGenerateLanguageSpecificationException {

        CompositeSymbolBuilder csb = new CompositeSymbolBuilder(m);
        ReferenceSymbolBuilder rsb = new ReferenceSymbolBuilder(m);
        PostReferenceSymbolBuilder prsb = new PostReferenceSymbolBuilder(m);
        TokenSymbolBuilder tsb = new TokenSymbolBuilder(m);
        
        BasicTokenBuilder btb = new BasicTokenBuilder(m);


        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        SyntacticSpecificationFactory ssf = new SyntacticSpecificationFactory();
        ssf.setTokenSymbolBuilder(tsb);
        ssf.setDataFactory(new ModelCCParserDataFactory());

        Map<PatternRecognizer,TokenSpecification> deltots = new HashMap<PatternRecognizer,TokenSpecification>();
        Map<ModelElement,ElementId> eltoeid = new HashMap<ModelElement,ElementId>();
        Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzero = new HashMap<ModelElement,Map<List<PatternRecognizer>,RuleElement>>();
        Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolist = new HashMap<ModelElement,Map<List<PatternRecognizer>,RuleElement>>();
        Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzeroref = new HashMap<ModelElement,Map<List<PatternRecognizer>,RuleElement>>();
        Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistref = new HashMap<ModelElement,Map<List<PatternRecognizer>,RuleElement>>();
        Map<ModelElement,TokenSpecification> elementTokenSpecifications = new HashMap<ModelElement,TokenSpecification>();
        Map<ModelElement,Set<Rule>> elementRules = new HashMap<ModelElement,Set<Rule>>();
        Map<PatternRecognizer,RuleElement> deltore = new HashMap<PatternRecognizer,RuleElement>();
        Map<ModelElement,RuleElement> eltore = new HashMap<ModelElement,RuleElement>();
        Map<ModelElement,RuleElement> eltoreref = new HashMap<ModelElement,RuleElement>();
        Set<Rule> listRules = new HashSet<Rule>();

        // Generate ElementIDs and RuleElements.
        for (Iterator<ModelElement> ite = m.getElements().iterator();ite.hasNext();) {
            ModelElement el = ite.next();
            ElementId eid = new ElementId(ElementType.ELEMENT,el,null,false);
            eltoeid.put(el,eid);
            RuleElement re = new RuleElement(eid);
            eltore.put(el,re);

            eid = new ElementId(ElementType.ELEMENT,el,null,true);
            re = new RuleElement(eid);
            eltoreref.put(el,re);
        }
        
        // -----------------
        // Lexical analysis.
        // -----------------


        // Delimiters to tokens.
        for (Iterator<PatternRecognizer> ite = m.getDelimiters().iterator();ite.hasNext();) {
        	PatternRecognizer pr = ite.next();
            TokenSpecification ts = new TokenSpecification(pr, pr);
        	lsf.addTokenSpecification(ts);
        	deltots.put(pr,ts);

            RuleElement re = new RuleElement(pr);
            // Hack: delimiter pattern matches empty string
            if (ts.getRecognizer().getArg().equals("")) {
            	Rule er = new Rule();
            	er.setLeft(re);
            	er.setBuilder(null);
            	er.setRight(new ArrayList<RuleElement>());
            	ssf.addRule(er);
            	deltore.put(pr,re);
            }
            else if (ts.getRecognizer().read("",0)!=null) {
            	RuleElement parent = new RuleElement(re); 
            	Rule er = new Rule();
            	er.setLeft(parent);
            	er.setBuilder(null);
            	List<RuleElement> lre = new ArrayList<RuleElement>();
            	lre.add(re);
            	er.setRight(lre);
            	ssf.addRule(er);
            	Rule er2 = new Rule();
            	er2.setLeft(parent);
            	er2.setBuilder(null);
            	er2.setRight(new ArrayList<RuleElement>());
            	ssf.addRule(er2);
            	deltore.put(pr,parent);

            }
            else {
            	deltore.put(pr,re);
            }
        }


        // Basic elements to tokens.
        for (Iterator<ModelElement> ite = m.getElements().iterator();ite.hasNext();) {
            ModelElement el = ite.next();
            if (BasicModelElement.class.isAssignableFrom(el.getClass())) {

                Rule r = new Rule();
                ElementId eid = eltoeid.get(el);
                ElementId beid = new ElementId(ElementType.BASIC,el,el.getSeparator(),false);
                RuleElement rel = new RuleElement(eid);
                r.setLeft(rel);

                List<RuleElement> rers = new ArrayList<RuleElement>();

                if (el.getPrefix()!=null)
                    for (int i = 0;i < el.getPrefix().size();i++)
                        rers.add(deltore.get(el.getPrefix().get(i)));

                RuleElement rer = new RuleElement(beid);
                rers.add(rer);

                if (el.getSuffix()!=null)
                    for (int i = 0;i < el.getSuffix().size();i++)
                        rers.add(deltore.get(el.getSuffix().get(i)));

                r.setRight(rers);

                r.setBuilder(dsb);

                ssf.addRule(r);
                Set<Rule> sr = elementRules.get(el);
                if (sr == null) {
                    sr = new HashSet<Rule>();
                    elementRules.put(el,sr);
                }
                sr.add(r);

                BasicModelElement bel = (BasicModelElement)el;

                // Hack: pattern matches empty string
            	
                if (bel.getPattern().read("",0)!=null) {
                    RuleElement rel2 = new RuleElement(eid);
                	Rule er = new Rule();
                	er.setLeft(rel2);
                	er.setRight(new ArrayList<RuleElement>());
                	er.setBuilder(dsb);
                	ssf.addRule(er);
                	sr.add(er);
                }
                
                TokenSpecification ts = new TokenSpecification(beid,bel.getPattern(),btb);
                lsf.addTokenSpecification(ts);
                elementTokenSpecifications.put(bel,ts);
            }
        }

        // Precedences
        /*
        for (Iterator<ModelElement> ite = m.getElements().iterator();ite.hasNext();) {
            ModelElement el = ite.next();
            if (BasicModelElement.class.isAssignableFrom(el.getClass())) {
                TokenSpecification ts = elementTokenSpecifications.get(el);
                if (ts != null) {
                    if (m.getPrecedences().get(el) != null) {
                        for (Iterator<ModelElement> ite2 = m.getPrecedences().get(el).iterator();ite2.hasNext();) {
                            ModelElement el2 = ite2.next();
                            TokenSpecification ts2 = elementTokenSpecifications.get(el2);
                            if (ts2 != null) {
                                lsf.addPrecedence(ts,ts2);
                            }
                        }
                    }
                }
            }
        }
*/
        // -----------------
        // Syntactic analysis.
        // -----------------

        // Composite elements.
        for (Iterator<ModelElement> ite = m.getElements().iterator();ite.hasNext();) {
            ModelElement el = ite.next();
            RuleElement elre = eltore.get(el);
            if (ComplexModelElement.class.isAssignableFrom(el.getClass())) {
            	List<MemberNode> nodes = new ArrayList<MemberNode>();
                ComplexModelElement ce = (ComplexModelElement)el;
                for (Iterator<ElementMember> cite = ce.getContents().iterator();cite.hasNext();) {
                	ElementMember current = cite.next();
                	nodes.add(new MemberNode(current,current.isOptional()));
                }
                
            	System.out.print("DEBUG   "+ce.getElementClass().getCanonicalName()+"  contains ");
                for (Iterator<MemberNode> nite = nodes.iterator();nite.hasNext();) {
                	MemberNode node = nite.next();
                	System.out.print("(");
                    for (Iterator<ElementMember> cite = node.getContents().iterator();cite.hasNext();) {
                    	ElementMember em = cite.next();
                    	System.out.print(em.getField());
                    	if (em.isOptional())
                    		System.out.print("?");
                    	if (cite.hasNext())
                    		System.out.print(" ");
                    }
                	System.out.print(") ");
                }
                System.out.println("");

                
                Set<List<ElementMember>> stage;

                //TODO
                // Primero, hacer divisiones oportunas para los opcionales
                // Segundo, ajustar los positions.
                // Tercero, si freeorder asociar de todas las formas, si no, no.
                if (ce.isFreeOrder()) {
                    stage = recManageFreeOrders(ce.getContents(),new ArrayList<ElementMember>());
                }
                else {
                    stage = recManageOptionals(ce.getContents(),new ArrayList<ElementMember>());
                }

                List<List<ElementMember>> optionals = new ArrayList<List<ElementMember>>();
                List<List<ElementMember>> nonoptionals = new ArrayList<List<ElementMember>>();
                List<Rule> roptionals = new ArrayList<Rule>();
                List<Rule> rnonoptionals = new ArrayList<Rule>();
                for (Iterator<List<ElementMember>> itex = stage.iterator();itex.hasNext();) {
                    List<ElementMember> act = itex.next();
                    if (hasOptional(act)) {
                        optionals.add(act);
                    }
                    else
                        nonoptionals.add(act);
                }

                /*
                for (int i = 0;i < optionals.size();i++) {
                    roptionals.add(createRule(m,elre,optionals.get(i),el,deltore, eltore, eltolist, eltolistzero,listRules,csb));
                }
                for (int i = 0;i < nonoptionals.size();i++) {
                    rnonoptionals.add(createRule(m,elre,nonoptionals.get(i),el,deltore, eltore, eltolist, eltolistzero,listRules,csb));
                }*/
                
                for (int i = 0;i < optionals.size();i++) {
                    roptionals.addAll(assocRule(m,ssf,elre,optionals.get(i),el,deltore, eltore, eltoreref,eltolist, eltolistzero,eltolistref,eltolistzeroref,listRules,csb));
                }
                for (int i = 0;i < nonoptionals.size();i++) {
                    rnonoptionals.addAll(assocRule(m,ssf,elre,nonoptionals.get(i),el,deltore, eltore, eltoreref,eltolist, eltolistzero,eltolistref,eltolistzeroref,listRules,csb));
                }

                CompositionType ctyp = ce.getComposition();
                switch (ctyp) {
                    case EAGER:
                        for (int i = 0;i < roptionals.size();i++)
                            for (int j = 0;j < rnonoptionals.size();j++)
                                ssf.addCompositionPrecedence(roptionals.get(i),rnonoptionals.get(j));
                        break;
                    case LAZY:
                        for (int i = 0;i < rnonoptionals.size();i++)
                            for (int j = 0;j < roptionals.size();j++)
                                ssf.addCompositionPrecedence(rnonoptionals.get(i),roptionals.get(j));
                        break;
                    case EXPLICIT:
                        for (int i = 0;i < rnonoptionals.size();i++)
                            for (int j = 0;j < roptionals.size();j++)
                                ssf.addCompositionPrecedence(rnonoptionals.get(i),roptionals.get(j));
                        for (int i = 0;i < roptionals.size();i++)
                            for (int j = 0;j < rnonoptionals.size();j++)
                                ssf.addCompositionPrecedence(roptionals.get(i),rnonoptionals.get(j));
                        break;
                    case UNDEFINED:
                        break;
                }

                Set<Rule> sr = elementRules.get(el);
                if (sr == null) {
                    sr = new HashSet<Rule>();
                    elementRules.put(el,sr);
                }
                sr.addAll(roptionals);
                sr.addAll(rnonoptionals);
                for (int i = 0;i < roptionals.size();i++)
                    ssf.addRule(roptionals.get(i));
                for (int i = 0;i < rnonoptionals.size();i++)
                    ssf.addRule(rnonoptionals.get(i));
                
                
                if (!ce.getIds().isEmpty()) {
                    elre = eltoreref.get(el);
                    
                    Set<List<ElementMember>> stageids;
                    if (ce.isFreeOrder()) {
                        stageids = recManageFreeOrders(ce.getIds(),new ArrayList<ElementMember>());
                    }
                    else {
                        stageids = recManageOptionals(ce.getIds(),new ArrayList<ElementMember>());
                    }
                    for (Iterator<List<ElementMember>> itex = stageids.iterator();itex.hasNext();) {
                        List<ElementMember> act = itex.next();
                        ssf.addRule(createRule(m,elre,act,null,deltore,eltore,eltoreref,eltolist,eltolistzero,eltolistref,eltolistzeroref,listRules,rsb,prsb));
                    }
                    
                }
                
            }
        }

        for (Iterator<Rule> ite = listRules.iterator();ite.hasNext();) {
            ssf.addRule(ite.next());
        }

        // Selector elements.
        for (Iterator<ModelElement> ite = m.getElements().iterator();ite.hasNext();) {
            ModelElement el = ite.next();
            ElementId eid = eltoeid.get(el);
            if (ChoiceModelElement.class.isAssignableFrom(el.getClass())) {
                if (m.getSubelements().get(el) != null) {
                    for (Iterator<ModelElement> ite2 = m.getSubelements().get(el).iterator();ite2.hasNext();) {
                        ModelElement el2 = ite2.next();
                        ElementId eid2 = eltoeid.get(el2);

                        Rule r = new Rule();
                        RuleElement rel = new RuleElement(eid);
                        r.setLeft(rel);

                        List<RuleElement> rers = new ArrayList<RuleElement>();

                        if (el.getPrefix()!=null)
                            for (int i = 0;i < el.getPrefix().size();i++)
                                rers.add(deltore.get(el.getPrefix().get(i)));

                        r.setRelevant(rers.size());

                        RuleElement rer = new RuleElement(eid2);
                        rers.add(rer);

                        if (el.getSuffix()!=null)
                            for (int i = 0;i < el.getSuffix().size();i++)
                                rers.add(deltore.get(el.getSuffix().get(i)));

                        r.setRight(rers);

                        r.setBuilder(dsb);

                        ssf.addRule(r);

                        Set<Rule> sr = elementRules.get(el);
                        if (sr == null) {
                            sr = new HashSet<Rule>();
                            elementRules.put(el,sr);
                        }
                        sr.add(r);

                    }
             }
            }
        }

        // Associativities.
        for (Iterator<ModelElement> ite = m.getElements().iterator();ite.hasNext();) {
            ModelElement el = ite.next();
            ElementId eid = eltoeid.get(el);
            if (el.getAssociativity() != AssociativityType.UNDEFINED) {
                switch (el.getAssociativity()) {
                    case LEFT_TO_RIGHT:
                        ssf.setAssociativity(eid,AssociativityConstraint.LEFT_TO_RIGHT);
                        break;
                    case RIGHT_TO_LEFT:
                        ssf.setAssociativity(eid,AssociativityConstraint.RIGHT_TO_LEFT);
                        break;
                    case NON_ASSOCIATIVE:
                        ssf.setAssociativity(eid,AssociativityConstraint.NON_ASSOCIATIVE);
                        break;
                }
            }
        }

        // Precedences
        for (Iterator<ModelElement> ite = m.getElements().iterator();ite.hasNext();) {
            ModelElement el = ite.next();
            if (ComplexModelElement.class.isAssignableFrom(el.getClass()) || ChoiceModelElement.class.isAssignableFrom(el.getClass())) {
                Set<Rule> sr = elementRules.get(el);
                if (sr != null) {
                    if (m.getPrecedences().get(el) != null) {
                        for (Iterator<ModelElement> ite2 = m.getPrecedences().get(el).iterator();ite2.hasNext();) {
                            ModelElement el2 = ite2.next();
                            Set<Rule> sr2 = elementRules.get(el2);
                            if (sr2 != null) {
                                for (Iterator<Rule> itr = sr.iterator();itr.hasNext();) {
                                    Rule r1 = itr.next();
                                    for (Iterator<Rule> itr2 = sr2.iterator();itr2.hasNext();) {
                                        Rule r2 = itr.next();
                                        if (ComplexModelElement.class.isAssignableFrom(el.getClass()) && ComplexModelElement.class.isAssignableFrom(el2.getClass()))
                                            ssf.addCompositionPrecedence(r1, r2);
                                        if (ChoiceModelElement.class.isAssignableFrom(el.getClass()) && ChoiceModelElement.class.isAssignableFrom(el2.getClass()))
                                            ssf.addSelectionPrecedence(r1, r2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        /*
        Integer start = new Integer(0);
        ssf.setStartType(start);
        List<RuleElement> right = new ArrayList<RuleElement>();

        if (m.getStart().getPrefix()!=null)
            for (int i = 0;i < m.getStart().getPrefix().size();i++)
                right.add(deltore.get(m.getStart().getPrefix().get(i)));
        right.add(eltore.get(m.getStart()));
        if (m.getStart().getSuffix()!=null)
            for (int i = 0;i < m.getStart().getSuffix().size();i++)
                right.add(deltore.get(m.getStart().getSuffix().get(i)));

        Rule r = new Rule(new RuleElement(start),right,null,stsb);
        ssf.addRule(r);*/

        ssf.setStartType(eltoeid.get(m.getStart()));


        
/*
        for (Iterator<TokenSpecification> iter = lsf.getTokenSpecifications().iterator();iter.hasNext();) {
            TokenSpecification rx = iter.next();
            System.out.println("token: "+rx);
        }


        for (Iterator<Rule> iter = ssf.getRules().iterator();iter.hasNext();) {
            Rule rx = iter.next();
            System.out.println("regla: "+rx);
        }
*/
 
 
        // -----------------
        // Build language specification.
        // -----------------

        LexicalSpecification ls = null;
        SyntacticSpecification ss = null;

        try {
            ls = lsf.create();
            ss = ssf.create();
        } catch (Exception e) {
            throw new CannotGenerateLanguageSpecificationException(e);
        }
        
        /*
        for (Iterator<Rule> ite = ss.getGrammar().getRules().iterator();ite.hasNext();) {
            System.out.println(ite.next());
        }
        System.out.println("---------------");
        System.out.println("---------------");
*/
        return new LanguageSpecification(ls,ss);
    }

    private Set<List<ElementMember>> recManageFreeOrders(List<ElementMember> elcs,List<ElementMember> act) {
        Set<List<ElementMember>> ret = new HashSet<List<ElementMember>>();
        int i;
        List<ElementMember> copy,actcopy;
        if (elcs.size() > 0) {
            for (i = 0;i < elcs.size();i++) {
                copy = new ArrayList<ElementMember>();
                copy.addAll(elcs);
                actcopy = new ArrayList<ElementMember>();
                actcopy.addAll(act);
                actcopy.add(elcs.get(i));
                copy.remove(i);
                ret.addAll(recManageFreeOrders(copy,actcopy));
            }
        }
        else {
            ret.addAll(recManageOptionals(act,new ArrayList<ElementMember>()));
        }
        return ret;
    }

    private Set<List<ElementMember>> recManageOptionals(List<ElementMember> elcs,List<ElementMember> act) {
        Set<List<ElementMember>> ret = new HashSet<List<ElementMember>>();
        int i;
        ArrayList<ElementMember> copy;
        ArrayList<ElementMember> act2 = new ArrayList<ElementMember>();
        act2.addAll(act);
        if (elcs.size() > 0) {
            copy = new ArrayList<ElementMember>();
            copy.addAll(elcs);
            while (!copy.isEmpty()) {
                if (copy.get(0).isOptional()) {
                    act.add(copy.get(0));
                    copy.remove(0);
                    ret.addAll(recManageOptionals(copy,act));
                    ret.addAll(recManageOptionals(copy,act2));
                    return ret;
                }
                else {
                    act.add(copy.get(0));
                    act2.add(copy.get(0));
                    copy.remove(0);
                }
            }
            if (!act.isEmpty())
                ret.add(act);
            return ret;
        }
        else {
            ret.add(act);
        }
        return ret;
    }

    private boolean hasOptional(List<ElementMember> act) {
        int j;
        for (j = 0;j < act.size();j++) {
            if (act.get(j).isOptional())
                return true;
        }
        return false;
    }


    private Rule createRule(Model m,RuleElement left,List<ElementMember> cts,ModelElement el,Map<PatternRecognizer,RuleElement> deltore,Map<ModelElement,RuleElement> eltore,Map<ModelElement,RuleElement> eltoreref,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolist,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzero,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistref,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzeroref,Set<Rule> listRules,SymbolBuilder sb) {
        return createRule(m,left,cts,el,deltore,eltore,eltoreref,eltolist,eltolistzero,eltolistref,eltolistzeroref,listRules,sb,null);
    }
    private Rule createRule(Model m,RuleElement left,List<ElementMember> cts,ModelElement el,Map<PatternRecognizer,RuleElement> deltore,Map<ModelElement,RuleElement> eltore,Map<ModelElement,RuleElement> eltoreref,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolist,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzero,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistref,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzeroref,Set<Rule> listRules,SymbolBuilder sb,PostSymbolBuilder psb) {
        List<RuleElement> right = new ArrayList<RuleElement>();
        int i;
        if (el != null)
            if (el.getPrefix()!=null)
                for (i = 0;i < el.getPrefix().size();i++)
                    right.add(deltore.get(el.getPrefix().get(i)));
        for (i = 0;i < cts.size();i++)
            right.addAll(createContent(m,cts.get(i),deltore,eltore,eltoreref,eltolist,eltolistzero,eltolistref,eltolistzeroref,listRules));
        if (el != null)
            if (el.getSuffix()!=null)
                for (i = 0;i < el.getSuffix().size();i++)
                    right.add(deltore.get(el.getSuffix().get(i)));
        Rule r;
        r = new Rule(left,right,null,sb,psb);
        return r;
    }

    private List<RuleElement> createContent(Model m,ElementMember ct,Map<PatternRecognizer,RuleElement> deltore,Map<ModelElement,RuleElement> eltore,Map<ModelElement,RuleElement> eltoreref,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolist,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzero,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistref,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzeroref,Set<Rule> listRules) {
        List<RuleElement> lre = new ArrayList<RuleElement>();
        int i;
        ModelElement el = m.getClassToElement().get(ct.getElementClass());
        if (ct.getPrefix()!=null)
            for (i = 0;i < ct.getPrefix().size();i++)
                lre.add(deltore.get(ct.getPrefix().get(i)));

        if (!MultipleElementMember.class.isAssignableFrom(ct.getClass())) {
            //DESystem.out.println("Clase "+el.getElementClass().getName());
            //DESystem.out.println("contiene "+((ElementId)eltore.get(el).getType()).getElement().getElementClass().getName());
            if (ct.isReference()) {
                lre.add(new RuleElementPosition(eltoreref.get(el).getType(),ct));
            }
            else {
                lre.add(new RuleElementPosition(eltore.get(el).getType(),ct));
            }
        }
        else {
            if (ct.isReference()) {
                lre.add(listElement(m,ct,deltore,eltoreref,eltolistref,eltolistzeroref,listRules,true));
            }
            else {
                lre.add(listElement(m,ct,deltore,eltore,eltolist,eltolistzero,listRules,false));
            }
        }
        if (ct.getSuffix()!=null)
            for (i = 0;i < ct.getSuffix().size();i++)
                lre.add(deltore.get(ct.getSuffix().get(i)));
        return lre;
    }

    private RuleElement listElement(Model m,ElementMember ct,Map<PatternRecognizer,RuleElement> deltore,Map<ModelElement,RuleElement> eltore,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolist,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzero,Set<Rule> listRules,boolean ref) {
        List<PatternRecognizer> separator = null;
        ModelElement el = m.getClassToElement().get(ct.getElementClass());
        if (ct.getSeparator() != null)
            separator = ct.getSeparator();
        else if (el.getSeparator() != null)
            separator = el.getSeparator();
        Map<List<PatternRecognizer>,RuleElement> l1 = eltolist.get(el);
        Map<List<PatternRecognizer>,RuleElement> l0 = eltolistzero.get(el);
        if (l1 == null) {
            l1 = new HashMap<List<PatternRecognizer>,RuleElement>();
            eltolist.put(el,l1);
        }
        if (l0 == null) {
            l0 = new HashMap<List<PatternRecognizer>,RuleElement>();
            eltolistzero.put(el,l0);
        }
        RuleElement re = l1.get(separator);
        RuleElement re0 = l0.get(separator);
        Rule r;
        ArrayList<RuleElement> rct;
        int i;
        if (re == null) {
            //L -> L E
            //L -> E
            ElementId id = new ElementId(ElementType.LIST,el,separator,ref);
            re = new RuleElement(id);
            l1.put(separator,re);

            rct = new ArrayList<RuleElement>();
            rct.add(eltore.get(el));
            r = new Rule(re,rct,null,lesb);
            listRules.add(r);

            rct = new ArrayList<RuleElement>();
            rct.add(eltore.get(el));
            if (separator!=null)
                for (i = 0;i < separator.size();i++)
                    rct.add(deltore.get(separator.get(i)));
            rct.add(re);
            r = new Rule(re,rct,null,lsb);
            listRules.add(r);
        }
        if (((MultipleElementMember)ct).getMinimumMultiplicity()==0) {
            if (re0 == null) {
                //L0 -> L
                //L0 -> epsilon
                ElementId id = new ElementId(ElementType.LISTZERO,el,separator,ref);
                re0 = new RuleElement(id);
                l0.put(separator,re0);

                rct = new ArrayList<RuleElement>();
                rct.add(re);
                r = new Rule(re0,rct,null,lasb);
                listRules.add(r);

                rct = new ArrayList<RuleElement>();
                r = new Rule(re0,rct,null,lzsb);
                listRules.add(r);
            }
            RuleElement ro = new RuleElementPosition(re0.getType(),ct);
            return ro;
        }
        else {
            RuleElement ro = new RuleElementPosition(re.getType(),ct);
            return ro;
        }

    }

    private Set<Rule> assocRule(Model m,SyntacticSpecificationFactory ssf,RuleElement left,List<ElementMember> elcs,ModelElement el,Map<PatternRecognizer,RuleElement> deltore,Map<ModelElement,RuleElement> eltore,Map<ModelElement,RuleElement> eltoreref,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolist,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzero,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistref,Map<ModelElement,Map<List<PatternRecognizer>,RuleElement>> eltolistzeroref,Set<Rule> listRules,CompositeSymbolBuilder csb) {
        Set<Rule> ret = new HashSet<Rule>();
        int i;
        int f = -1;
        boolean found = false;
        boolean err = false;
        if (elcs.size() > 2) {
            for (i = 0;i < elcs.size();i++) {
//                if (elcs.get(i).getElementClass() != null) {
                    if (elcs.get(i).getElementClass().isAssignableFrom(el.getElementClass())) {
                        if (i>0) {
                            if (hasSubAsoc(m,m.getClassToElement().get(elcs.get(i-1).getElementClass()))) {
                                if (!found) found = true;
                                else if (i-1 != f) err = true;
                                f = i-1;
                            }
                        }
                        if (i<elcs.size()-1) {
                            if (hasSubAsoc(m,m.getClassToElement().get(elcs.get(i+1).getElementClass()))) {
                                if (!found) found = true;
                                else if (i+1 != f) err = true;
                                f = i+1;
                            }
                        }
                    }
             //   }
            }
        }
        if (err || !found)
            ret.add(createRule(m,left,elcs,el,deltore,eltore,eltoreref,eltolist,eltolistzero,eltolistref,eltolistzeroref,listRules,csb));
        else if (found) {

            
            //Rule rx = createRule(m,left,elcs,el,deltore,eltore,eltolist,eltolistzero,listRules);

            /*System.out.println("PROPAGA FIELD "+f+" EN "+el.getElementClass().getSimpleName());
            System.out.print("regla original: "+rx.getLeft().getType()+" ::=");
            for (Iterator<RuleElement> itere = rx.getRight().iterator();itere.hasNext();) {
                RuleElement rf = itere.next();
                System.out.print(" "+rf.getType());
            }
            System.out.println();
            */

            
            ModelElement sc;
            Iterator<ModelElement> ite;
            HashMap<Integer,ArrayList<Rule>> rs = new HashMap<Integer,ArrayList<Rule>>();
            ElementMember rep;
            Map<Rule,ModelElement> rules = new HashMap<Rule,ModelElement>();
            Map<Rule,Set<ModelElement>> precedes = new HashMap<Rule,Set<ModelElement>>();
            ModelElement e = m.getClassToElement().get(elcs.get(f).getElementClass());
            for (ite = m.getSubelements().get(e).iterator();ite.hasNext();) {
                sc = ite.next();
                ArrayList<ElementMember> elcc = new ArrayList<ElementMember>();
                elcc.addAll(elcs);
                rep = elcc.get(f);
                elcc.remove(f);

                String field = rep.getField();
                boolean optional = rep.isOptional();
                List<PatternRecognizer> prefix = rep.getPrefix();
                List<PatternRecognizer> suffix = rep.getSuffix();
                List<PatternRecognizer> separator = rep.getSeparator();
                Class contentClass = sc.getElementClass();
                boolean id = rep.isId();
                boolean reference = rep.isReference();

                ElementMember ctx;

                if (rep.getClass().equals(MultipleElementMember.class)) {
                    CollectionType collection = ((MultipleElementMember)rep).getCollection();
                    int minimumMultiplicity = ((MultipleElementMember)rep).getMinimumMultiplicity();
                    int maximumMultiplicity = ((MultipleElementMember)rep).getMaximumMultiplicity();
                    ctx = new MultipleElementMember(field,contentClass,optional,id,reference,prefix,suffix,separator,collection,minimumMultiplicity,maximumMultiplicity);
                }
                else {
                    ctx = new ElementMember(field,contentClass,optional,id,reference,prefix,suffix,separator);
                }
                elcc.add(f,ctx);

                Rule r = createRule(m,left,elcc,el,deltore,eltore,eltoreref,eltolist,eltolistzero,eltolistref,eltolistzeroref,listRules,csb);
                ret.add(r);

                /*
                System.out.print("regla producida: "+r.getLeft().getType()+" ::=");
                for (Iterator<RuleElement> itere = r.getRight().iterator();itere.hasNext();) {
                    RuleElement rf = itere.next();
                    System.out.print(" "+rf.getType());
                }
                System.out.println();*/

                rules.put(r,m.getClassToElement().get(sc.getElementClass()));
                ModelElement elrep = m.getClassToElement().get(sc.getElementClass());
                if (m.getPrecedences().get(elrep) != null) {
                    precedes.put(r,m.getPrecedences().get(elrep));
                }
            }

            for (Iterator<Rule> iter = rules.keySet().iterator();iter.hasNext();) {
                Rule r1 = iter.next();
                if (precedes.get(r1) != null) {
                    for (Iterator<Rule> iter2 = rules.keySet().iterator();iter2.hasNext();) {
                        Rule r2 = iter2.next();
                        if (precedes.get(r1).contains(rules.get(r2))) {
                            ssf.addCompositionPrecedence(r1, r2);
                            //ssf.addSelectionPrecedence(r1, r2);
                        }
                    }
                }
            }
            
        }



        return ret;
    }

    private boolean hasSubAsoc(Model m,ModelElement el) {
        ModelElement sc;
        Iterator<ModelElement> ite;
        int prio = -1;
        Set<ModelElement> precededs = new HashSet<ModelElement>();
        if (m.getSubelements().get(el) != null) {
            for (ite = m.getSubelements().get(el).iterator();ite.hasNext();) {
                sc = ite.next();
                if (m.getPrecedences().get(sc) != null)
                    precededs.addAll(m.getPrecedences().get(sc));
            }


            for (ite = m.getSubelements().get(el).iterator();ite.hasNext();) {
                sc = ite.next();
                if (precededs.contains(sc))
                    return true;
            }
        }
        return false;
    }

}
