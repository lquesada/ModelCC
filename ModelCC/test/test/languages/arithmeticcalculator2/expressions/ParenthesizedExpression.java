/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator2.expressions;

import test.languages.arithmeticcalculator2.Expression2;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Prefix("\\(")
@Suffix("\\)")
public class ParenthesizedExpression extends Expression2 implements IModel {
    Expression2 e;

    @Override
    public double eval() {
        return e.eval();
    }
}
