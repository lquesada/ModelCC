/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.separate;

import org.modelcc.*;
import test.org.modelcc.examples.language.mapping.separate.ConstraintSpecification;
import test.org.modelcc.examples.language.mapping.separate.Element;
import test.org.modelcc.examples.language.mapping.separate.Identifier;

/**
 *
 * @author elezeta
 */
public class ConstraintDefinition implements IModel {
    Element target;
    
    @Optional
    Identifier constraintID;
    
    @Optional
    ConstraintSpecification constraint;
}
