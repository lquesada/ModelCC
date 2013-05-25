/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.mapping;

import org.modelcc.*;

/**
 *
 * @author elezeta
 * @serial
 */
public class ConstraintDefinition implements IModel {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    Element target;
    
    @Optional
    @Prefix("#")
    Identifier constraintID;
    
    @Optional
    @Prefix(":")
    ConstraintSpecification constraint;
}
