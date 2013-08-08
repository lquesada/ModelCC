/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.probabilistic;

import java.io.Serializable;

/**
 * Probability value.
 * @author elezeta
 */
public abstract class ProbabilityValue implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

	public abstract double getNumericValue();
	
	public abstract ProbabilityValue product(ProbabilityValue other);

	public abstract ProbabilityValue complementary();
	
}
