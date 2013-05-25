/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator2.expressions;

import test.languages.arithmeticcalculator2.BinaryOperator;
import test.languages.arithmeticcalculator2.Expression2;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class BinaryExpression extends Expression2 implements IModel {
    Expression2 e1;
    BinaryOperator op;
    Expression2 e2;

    @Override
    public double eval() {
        return op.eval(e1,e2);
    }
}
