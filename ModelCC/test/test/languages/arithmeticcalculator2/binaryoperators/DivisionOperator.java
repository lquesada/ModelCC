/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator2.binaryoperators;

import test.languages.arithmeticcalculator2.BinaryOperator;
import test.languages.arithmeticcalculator2.Expression2;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Priority(value=4)
@Pattern(regExp="\\/")
public class DivisionOperator extends BinaryOperator implements IModel {

    @Override
    public double eval(Expression2 e1, Expression2 e2) {
        return e1.eval()/e2.eval();
    }

}
