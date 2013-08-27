/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.simplearithmeticexpression.expressions;

import org.modelcc.examples.language.simplearithmeticexpression.Expression;
import org.modelcc.examples.language.simplearithmeticexpression.UnaryOperator;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Priority(precedes=BinaryExpression.class)
public class UnaryExpression extends Expression implements IModel {
    UnaryOperator op;
    Expression e;

    @Override
    public double eval() {
        return op.eval(e);
    }
}
