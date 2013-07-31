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
public class WrongClass24 implements IModel {
	@Probability(evaluator=OKClass.class)
	OKClass a;
	OKClass b;
}
