/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.probabilistic;

import org.modelcc.metamodel.ModelElement;

/**
 * Numeric probability evaluator.
 * @author elezeta
 * @serial
 */
public class NumericProbabilityEvaluator extends ProbabilityEvaluator { 

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private NumericProbabilityValue value;
    
    public NumericProbabilityEvaluator(String args) {
    	super(args);
    	value = new NumericProbabilityValue(Double.parseDouble(args));
    }

    public NumericProbabilityEvaluator(double value) {
    	super("");
    	this.value = new NumericProbabilityValue(value);
    }

    @Override
	public ProbabilityValue evaluate(Object object,ModelElement element) {
    	return this.value;
	}

}
