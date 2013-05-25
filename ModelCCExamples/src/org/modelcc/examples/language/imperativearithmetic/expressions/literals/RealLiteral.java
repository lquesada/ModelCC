/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic.expressions.literals;

import org.modelcc.examples.language.imperativearithmetic.expressions.LiteralExpression;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class RealLiteral extends LiteralExpression implements IModel {

    @Value double value;

    @Override
    public double eval() {
        return value;
    }

}
