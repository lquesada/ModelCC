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
public class Test9b implements IModel {
   
	@Probability(p=0.2)
	@Multiplicity(minimum=0)
    public List<OK05Class> a;

	@Position(element="a",position=Position.BEFORELAST)
	@Optional
	@Probability(p=0.1)
	public OK01Class b;
}
