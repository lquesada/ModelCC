/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator.expressions.literals;

import test.languages.arithmeticcalculator.expressions.LiteralExpression;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class IntegerLiteral extends LiteralExpression implements IModel {

    @Value public int value;

    @Override
    public double eval() {
        return (double)value;
    }

}
