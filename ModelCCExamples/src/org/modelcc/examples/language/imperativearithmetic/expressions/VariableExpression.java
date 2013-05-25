/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic.expressions;

import org.modelcc.examples.language.imperativearithmetic.Expression;
import org.modelcc.*;
import org.modelcc.examples.language.imperativearithmetic.Variable;

/**
 * @author elezeta
 */
public class VariableExpression extends Expression implements IModel {

    @Reference
    private Variable var;

    @Override
    public double eval() {
        return var.getValue();
    }
}
