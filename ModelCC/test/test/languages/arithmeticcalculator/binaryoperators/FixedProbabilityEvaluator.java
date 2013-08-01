/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator.binaryoperators;

import org.modelcc.probabilistic.InvalidProbabilityValueException;
import org.modelcc.probabilistic.NumericProbabilityValue;
import org.modelcc.probabilistic.ProbabilityEvaluator;
import org.modelcc.probabilistic.ProbabilityValue;

/**
 *
 * @author elezeta
 */
public class FixedProbabilityEvaluator extends ProbabilityEvaluator {

	public FixedProbabilityEvaluator(String args) throws InvalidProbabilityValueException {
		super(args);
	}

	@Override
	public ProbabilityValue evaluate(Object object) {
		return new NumericProbabilityValue(0.1d);
	}

}
