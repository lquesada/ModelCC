/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.probabilities;

import java.util.List;

import org.modelcc.*;
import org.modelcc.probabilistic.Probability;

/**
 *
 * @author elezeta
 */
@Probability(p=0.2)
public class Test8 implements IModel {
   
	@Probability(p=0.2)
	@Minimum(0)
    public List<OK05Class> a;

	@Position(element="a",position=Position.WITHIN)
	@Optional
	public OK01Class b;
}
