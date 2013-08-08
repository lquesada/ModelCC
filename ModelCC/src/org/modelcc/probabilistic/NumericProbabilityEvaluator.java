/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.probabilistic;

import java.io.Serializable;

/**
 * Numeric probability evaluator.
 * @author elezeta
 * @serial
 */
public class NumericProbabilityEvaluator extends ProbabilityEvaluator implements Serializable { 

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private NumericProbabilityValue value;
    
    public NumericProbabilityEvaluator(String args) throws InvalidProbabilityValueException {
    	super(args);
    	double value = Double.parseDouble(args);
    	if (value < 0 || value>1) {
    		throw new InvalidProbabilityValueException("Invalid probability value.");
    	}
    	this.value = new NumericProbabilityValue(value);
    }

    public NumericProbabilityEvaluator(double value) throws InvalidProbabilityValueException {
    	super("");
    	if (value < 0 || value>1) {
    		throw new InvalidProbabilityValueException("Invalid probability value.");
    	}
    	this.value = new NumericProbabilityValue(value);
    }

    @Override
	public ProbabilityValue evaluate(Object object) {
    	return this.value;
	}

}
