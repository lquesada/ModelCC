/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic.expressions;

import org.modelcc.examples.language.imperativearithmetic.Expression;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Prefix("\\(")
@Suffix("\\)")
public class ParenthesizedExpression extends Expression implements IModel {
    
    private Expression e;

    @Override
    public double eval() {
        return e.eval();
    }
}
