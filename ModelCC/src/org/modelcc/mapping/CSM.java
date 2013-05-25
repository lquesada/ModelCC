/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.mapping;

import java.util.Set;
import org.modelcc.*;

/**
 *
 * @author elezeta
 * @serial
 */
public class CSM implements IModel {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Minimum(0)
    Set<ConstraintDefinition> constraints;
    
}
