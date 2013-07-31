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
@Probability(evaluator=ExceptionProbabilityEvaluator.class)
public class WrongClass29 implements IModel {
	OKClass a;
	OKClass b;
}
