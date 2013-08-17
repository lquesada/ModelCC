/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Syntactic specification factory.
 * @author elezeta
 * @serial
 */
public final class SyntacticSpecificationFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Grammar factory.
     */
    private GrammarFactory gf;

    /**
     * Constraints factory.
     */
    private ConstraintsFactory cf;

    /**
     * Default constructor.
     */
    public SyntacticSpecificationFactory() {
        gf = new GrammarFactory();
        cf = new ConstraintsFactory();

    }

    /**
     * Set an associativity constraint for an object type.
     * @param type the object type.
     * @param as the associativity constraint.
     */
    public void setAssociativity(Object type,AssociativityConstraint as) {
        cf.setAssociativity(type,as);
    }

    /**
     * @return the associativities
     */
    public Map<Object, AssociativityConstraint> getAssociativities() {
        return cf.getAssociativities();
    }

    /**
     * @return the composition precedences.
     */
    public Map<Rule, Set<Rule>> getCompositionPrecedences() {
        return cf.getCompositionPrecedences();
    }

    /**
     * Adds a composition precedence between rules.
     * @param ts1 the rule that precedes.
     * @param ts2 the rule that is preceded.
     */
    public void addCompositionPrecedence(Rule ts1,Rule ts2) {
        cf.addCompositionPrecedences(ts1,ts2);
    }

    /**
     * Removes a precedence relationship between tokens.
     * @param ts1 the token that precedes.
     * @param ts2 the token that is preceded.
     */
    public void removeCompositionPrecedence(Rule ts1,Rule ts2) {
        cf.removeCompositionPrecedences(ts1,ts2);
    }


    /**
     * @return the selection precedences
     */
    public Map<Rule, Set<Rule>> getSelectionPrecedences() {
        return cf.getSelectionPrecedences();
    }

    /**
     * Adds an selection precedence between rules.
     * @param ts1 the rule that precedes.
     * @param ts2 the rule that is preceded.
     */
    public void addSelectionPrecedence(Rule ts1,Rule ts2) {
        cf.addSelectionPrecedences(ts1, ts2);
    }

    /**
     * Removes an selection precedence between rules.
     * @param ts1 the rule that precedes.
     * @param ts2 the rule that is preceded.
     */
    public void removeSelectionPrecedences(Rule ts1,Rule ts2) {
        cf.removeSelectionPrecedences(ts1, ts2);        
    }

    /**
     * Adds a rule.
     * @param r the rule to add.
     */
    public void addRule(Rule r) {
        gf.addRule(r);
    }

    /**
     * Adds an empty element
     * @param r the empty element to add.
     */
    public void addEmptyElement(Object re) {
        gf.addEmptyElement(re);
    }

    /**
     * Removes a rule.
     * @param r the rule to remove.
     */
    public void removeRule(Rule r) {
        gf.removeRule(r);
    }

    /**
     * @return the rule set.
     */
    public Set<Rule> getRules() {
        return gf.getRules();
    }

    /**
     * @return the start type
     */
    public Object getStartType() {
        return gf.getStartType();
    }

    /**
     * @param startType the start type to set
     */
    public void setStartType(Object startType) {
        gf.setStartType(startType);
    }
    
    /**
     * @param sb the token symbol builder.
     */
    public void setTokenSymbolBuilder(SymbolBuilder sb) {
        gf.setTokenSymbolBuilder(sb);
    }

    /**
     * @param dataFactory the dataFactory to set
     */
    public void setDataFactory(ParserDataFactory dataFactory) {
        gf.setDataFactory(dataFactory);
    }

    /**
     * Generates a syntactic specification.
     * @throws CyclicCompositionPrecedenceException whenever several rules mutually precede with a composition precedence.
     * @throws CyclicSelectionPrecedenceException whenever several rules mutually precede with an selection precedence.
     * @throws NullRuleElementException whenever a rule element is null
     * @return the syntactic specification.
     */
    public SyntacticSpecification create() throws NullRuleElementException, CyclicCompositionPrecedenceException, CyclicSelectionPrecedenceException {
        return new SyntacticSpecification(gf.create(),cf.create());
    }

    /**
     * @param builder the builder to set
     */
	public void addEmptyElementBuilder(SymbolBuilder dsb) {
		gf.setEmptyElementBuilder(dsb);		
	}
}
