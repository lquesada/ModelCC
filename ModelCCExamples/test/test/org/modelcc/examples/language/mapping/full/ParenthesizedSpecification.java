/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.full;

import org.modelcc.*;
import org.modelcc.mapping.ConstraintSpecification;

/**
 *
 * @author elezeta
 */
public class ParenthesizedSpecification extends ConstraintSpecification implements IModel {
    @Prefix("\\(")
    @Suffix("\\)")
    ConstraintSpecification constraint;
}
