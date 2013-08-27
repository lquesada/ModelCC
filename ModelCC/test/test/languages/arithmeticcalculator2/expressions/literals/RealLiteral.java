/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator2.expressions.literals;

import test.languages.arithmeticcalculator2.expressions.LiteralExpression;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Pattern(regExp="[0-9]+\\.[0-9]*")
public class RealLiteral extends LiteralExpression implements IModel {

    @Value double value;

    @Override
    public double eval() {
        return value;
    }

}
