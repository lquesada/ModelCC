/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.full;

import org.modelcc.*;
import org.modelcc.mapping.AlternationSpecification;
import org.modelcc.mapping.ConstraintSpecification;

/**
 *
 * @author elezeta
 */
@Priority(precedes=AlternationSpecification.class)
public class PrecedenceSpecification extends ConstraintSpecification implements IModel {
    @Separator("\\<")
    ConstraintSpecification[] constraints;
}
