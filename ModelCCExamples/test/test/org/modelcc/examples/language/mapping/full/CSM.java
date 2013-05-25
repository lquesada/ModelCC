/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.full;

import java.util.Set;
import org.modelcc.*;
import org.modelcc.mapping.ConstraintDefinition;

/**
 *
 * @author elezeta
 */
public class CSM implements IModel {
    @Minimum(0)
    Set<ConstraintDefinition> constraints;
    
}
