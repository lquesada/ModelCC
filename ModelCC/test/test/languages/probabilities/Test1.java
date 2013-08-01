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
public class Test1 implements IModel {
   
	@Probability(p=0.8)
    public OK05Class a;

}
