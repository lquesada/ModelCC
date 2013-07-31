/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.wronglanguages;

import org.modelcc.*;
import org.modelcc.probabilistic.Probability;

/**
 *
 * @author elezeta
 */
public class WrongClass25 implements IModel {
	@Probability(evaluator=ExceptionProbabilityEvaluator.class)
	OKClass a;
	OKClass b;
}
