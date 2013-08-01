/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.probabilistic;

import java.io.Serializable;
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
public abstract class ProbabilitySpecification implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Element probability map
     */
    private Map<ModelElement,ProbabilityEvaluator> elementProbabilities;

    /**
     * Element member probability map
     */
    private Map<ModelElement,Map<ElementMember,ProbabilityEvaluator>> memberProbabilities;
    
    public ProbabilitySpecification(Map<ModelElement,ProbabilityEvaluator> elementProbabilities,Map<ModelElement,Map<ElementMember,ProbabilityEvaluator>> memberProbabilities) {
    	this.elementProbabilities = elementProbabilities;
    	this.memberProbabilities = memberProbabilities;
    }
    
    public Map<ModelElement,ProbabilityEvaluator> getElementProbabilities() {
    	return Collections.unmodifiableMap(elementProbabilities);
    }

    public Map<ModelElement,Map<ElementMember,ProbabilityEvaluator>> getMemberProbabilities() {
    	return Collections.unmodifiableMap(memberProbabilities);
    }
}
