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
public class WrongClass26 implements IModel {
	@Probability(p=-0.3)
	OKClass a;
	OKClass b;
}
