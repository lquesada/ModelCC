/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.simplearithmeticexpression.expressions;

import org.modelcc.examples.language.simplearithmeticexpression.Expression;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Prefix("\\(")
@Suffix("\\)")
public class ExpressionGroup extends Expression implements IModel {
    Expression e;

    @Override
    public double eval() {
        return e.eval();
    }
}
