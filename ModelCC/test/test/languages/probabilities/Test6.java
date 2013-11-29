/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.probabilities;

import java.util.Set;

import org.modelcc.*;
import org.modelcc.probabilistic.Probability;

/**
 *
 * @author elezeta
 */
@Probability(p=0.2)
public class Test6 implements IModel {
   
	@Probability(p=0.2)
	@Multiplicity(minimum=0)
    public Set<OK05Class> a;

}
