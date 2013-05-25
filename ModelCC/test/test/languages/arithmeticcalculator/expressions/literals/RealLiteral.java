/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator.expressions.literals;

import test.languages.arithmeticcalculator.expressions.LiteralExpression;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class RealLiteral extends LiteralExpression implements IModel {

    @Value public double value;

    @Override
    public double eval() {
        return value;
    }

}
