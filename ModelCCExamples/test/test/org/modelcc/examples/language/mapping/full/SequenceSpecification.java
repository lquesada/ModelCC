/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.full;

import org.modelcc.*;
import org.modelcc.mapping.AlternationSpecification;
import org.modelcc.mapping.ConstraintSpecification;
import org.modelcc.mapping.PrecedenceSpecification;

/**
 *
 * @author elezeta
 */
@Priority(precedes={AlternationSpecification.class,PrecedenceSpecification.class})
public class SequenceSpecification extends ConstraintSpecification implements IModel {
    ConstraintSpecification[] constraints;
}
