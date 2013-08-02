/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.probabilistic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.modelcc.probabilistic.ProbabilityEvaluator;

/**
 * Probability specification factory.
 * @author elezeta
 * @serial
 */
public final class ProbabilitySpecificationFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Element probability map
     */
    private Map<Class,ProbabilityEvaluator> elementProbabilities;

    /**
     * Element member probability map
     */
    private Map<Field,ProbabilityEvaluator> memberProbabilities;
    
    /**
     * Default constructor.
     */
    public ProbabilitySpecificationFactory() {
        elementProbabilities = new HashMap<Class,ProbabilityEvaluator>();
        memberProbabilities = new HashMap<Field,ProbabilityEvaluator>();
    }

    /**
     * Adds an element probability.
     * @param c the element class.
     * @param ev the element probability.
     */
    public void addElementProbability(Class c,ProbabilityEvaluator ev) {
    	elementProbabilities.put(c,ev);
    }

    /**
     * Removes an element probability
     * @param c the element class.
     */
    public void removeElementProbability(Class c) {
        elementProbabilities.remove(c);
    }

    /**
     * Adds an element member probability.
     * @param c the member field
     * @param ev the member probability.
     */
    public void addMemberProbability(Field f,ProbabilityEvaluator ev) {
    	memberProbabilities.put(f,ev);
    }

    /**
     * Removes a member probability
     * @param f the member field.
     */
    public void removeMemberProbability(Field f) {
        memberProbabilities.remove(f);
    }

    /**
     * @return the element probability map.
     */
    public Map<Class,ProbabilityEvaluator> getElementProbabilities() {
        return Collections.unmodifiableMap(elementProbabilities);
    }

    /**
     * @return the member probability map.
     */
    public Map<Field,ProbabilityEvaluator> getMemberProbabilities() {
        return Collections.unmodifiableMap(memberProbabilities);
    }

    /**
     * Creates a probability specification.
     * @return the probability Specification.
     */
    public ProbabilitySpecification create() {

        Map<Class,ProbabilityEvaluator> elementProbabilitiesCopy = new HashMap<Class,ProbabilityEvaluator>();

        Map<Field,ProbabilityEvaluator> memberProbabilitiesCopy = new HashMap<Field,ProbabilityEvaluator>();
        
        elementProbabilitiesCopy.putAll(elementProbabilities);

        memberProbabilitiesCopy.putAll(memberProbabilities);
        
        return new ProbabilitySpecification(elementProbabilities,memberProbabilities);
    }

}
