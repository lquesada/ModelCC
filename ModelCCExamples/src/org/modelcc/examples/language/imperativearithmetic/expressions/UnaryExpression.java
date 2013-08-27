/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic.expressions;

import org.modelcc.examples.language.imperativearithmetic.Expression;
import org.modelcc.examples.language.imperativearithmetic.UnaryOperator;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Priority(precedes=BinaryExpression.class)
public class UnaryExpression extends Expression implements IModel {
    
    private UnaryOperator op;
    
    private Expression e;

    @Override
    public double eval() {
        return op.eval(e);
    }
}
