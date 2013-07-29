/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.probabilistic;

/**
 * Probability evaluator.
 * @author elezeta
 */
public abstract class ProbabilityValue {
	
	public abstract double getNumericValue();
	
	public abstract ProbabilityValue product(ProbabilityValue other);

	public abstract ProbabilityValue complementary();
	
}
