/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.probabilities;

import org.modelcc.*;
import org.modelcc.probabilistic.Probability;

/**
 *
 * @author elezeta
 */
@Probability(p=0.2)
public class Test3b implements IModel {
   
	@Probability(p=0.2)
    @Optional
    public OK05Class a;

	@Probability(p=0.8)
    @Optional
    public OK05Class b;

}
