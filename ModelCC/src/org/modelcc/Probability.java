/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.modelcc.probabilistic.ProbabilityEvaluator;

/**
 * Probability.
 * @author elezeta
 */
@Target({ElementType.TYPE,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Probability {

	public double p() default -1;
	
	//public int frequency() default -1;
	
	public Class evaluator() default Probability.class;
	
}
