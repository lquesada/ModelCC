/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.simplearithmeticexpression.expressions;

import org.modelcc.examples.language.simplearithmeticexpression.BinaryOperator;
import org.modelcc.examples.language.simplearithmeticexpression.Expression;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class BinaryExpression extends Expression implements IModel {
    Expression e1;
    BinaryOperator op;
    Expression e2;

    @Override
    public double eval() {
        return op.eval(e1,e2);
    }
}
