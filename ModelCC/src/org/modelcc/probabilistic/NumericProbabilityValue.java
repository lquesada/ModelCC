/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.probabilistic;

import java.io.Serializable;

/**
 * Numeric probability value.
 * @author elezeta
 * @serial
 */
public class NumericProbabilityValue extends ProbabilityValue implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    double value;

    public NumericProbabilityValue(double value) {
    	this.value = value;
    }
    
	@Override
	public double getNumericValue() {
		return value;
	}

	@Override
	public ProbabilityValue product(ProbabilityValue other) {
		return new NumericProbabilityValue(value*other.getNumericValue());
	}
	
    @Override
	public ProbabilityValue complementary() {
    	return new NumericProbabilityValue(1d-value);
    }

}
