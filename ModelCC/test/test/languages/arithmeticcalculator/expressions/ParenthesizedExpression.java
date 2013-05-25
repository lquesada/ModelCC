/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator.expressions;

import test.languages.arithmeticcalculator.Expression;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Prefix("\\(")
@Suffix("\\)")
public class ParenthesizedExpression extends Expression implements IModel {
    public Expression e;

    @Override
    public double eval() {
        return e.eval();
    }
}
