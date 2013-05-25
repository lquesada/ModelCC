/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.full;

import org.modelcc.*;
import org.modelcc.mapping.ConstraintSpecification;
import org.modelcc.mapping.Element;
import org.modelcc.mapping.Identifier;

/**
 *
 * @author elezeta
 */
public class ConstraintDefinition implements IModel {
    Element target;
    
    @Optional
    @Prefix("#")
    Identifier constraintID;
    
    @Optional
    @Prefix(":")
    ConstraintSpecification constraint;
}
