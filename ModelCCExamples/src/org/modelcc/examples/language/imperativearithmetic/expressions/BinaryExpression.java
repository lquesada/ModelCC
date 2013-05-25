/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic.expressions;

import org.modelcc.examples.language.imperativearithmetic.BinaryOperator;
import org.modelcc.examples.language.imperativearithmetic.Expression;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class BinaryExpression extends Expression implements IModel {
    
    private Expression e1;
    
    private BinaryOperator op;
    
    private Expression e2;

    @Override
    public double eval() {
        return op.eval(e1,e2);
    }
}
