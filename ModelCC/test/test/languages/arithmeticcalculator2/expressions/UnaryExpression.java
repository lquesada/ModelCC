/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator2.expressions;

import test.languages.arithmeticcalculator2.Expression2;
import test.languages.arithmeticcalculator2.UnaryOperator;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class UnaryExpression extends Expression2 implements IModel {
    UnaryOperator op;
    Expression2 e;

    @Override
    public double eval() {
        return op.eval(e);
    }
}
