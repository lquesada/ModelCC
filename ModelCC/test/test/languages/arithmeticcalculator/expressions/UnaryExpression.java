/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator.expressions;

import test.languages.arithmeticcalculator.Expression;
import test.languages.arithmeticcalculator.UnaryOperator;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class UnaryExpression extends Expression implements IModel {
    public UnaryOperator op;
    public Expression e;

    @Override
    public double eval() {
        return op.eval(e);
    }
}
