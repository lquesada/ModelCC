/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.probabilistic;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;

import org.modelcc.metamodel.ElementMember;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.probabilistic.ProbabilityEvaluator;

/**
 * Probability specification.
 * @author elezeta
 * @serial
 */
public class ProbabilitySpecification implements Serializable {

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
    
    public ProbabilitySpecification(Map<Class,ProbabilityEvaluator> elementProbabilities,Map<Field,ProbabilityEvaluator> memberProbabilities) {
    	this.elementProbabilities = elementProbabilities;
    	this.memberProbabilities = memberProbabilities;
    }
    
    public Map<Class,ProbabilityEvaluator> getElementProbabilities() {
    	return Collections.unmodifiableMap(elementProbabilities);
    }

    public Map<Field,ProbabilityEvaluator> getMemberProbabilities() {
    	return Collections.unmodifiableMap(memberProbabilities);
    }
}
