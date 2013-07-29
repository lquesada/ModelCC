/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.probabilistic;

import org.modelcc.metamodel.ModelElement;

/**
 * Probability evaluator.
 * @author elezeta
 * @serial
 */
public abstract class ProbabilityEvaluator {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

	public abstract ProbabilityValue evaluate(Object object,ModelElement element);
	
}
