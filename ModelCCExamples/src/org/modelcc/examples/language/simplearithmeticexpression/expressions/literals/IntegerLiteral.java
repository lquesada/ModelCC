/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.simplearithmeticexpression.expressions.literals;

import org.modelcc.examples.language.simplearithmeticexpression.expressions.LiteralExpression;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class IntegerLiteral extends LiteralExpression implements IModel {

    @Value int value;

    @Override
    public double eval() {
        return (double)value;
    }

}
