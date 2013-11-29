/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.separate;

import java.util.Set;
import org.modelcc.*;

import test.org.modelcc.examples.language.mapping.separate.ConstraintDefinition;

/**
 *
 * @author elezeta
 */
public class CSM implements IModel {
	@Multiplicity(minimum=0)
    Set<ConstraintDefinition> constraints;
    
}
