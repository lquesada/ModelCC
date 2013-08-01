/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.wronglanguages;

import org.modelcc.*;
import org.modelcc.probabilistic.InvalidProbabilityValueException;
import org.modelcc.probabilistic.ProbabilityEvaluator;
import org.modelcc.probabilistic.ProbabilityValue;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="a")
public class ExceptionProbabilityEvaluator extends ProbabilityEvaluator {

	public ExceptionProbabilityEvaluator(String args) throws InvalidProbabilityValueException {
		super(args);
		int a = 0/0;
	}

	@Override
	public ProbabilityValue evaluate(Object object) {
		return null;
	}

}
