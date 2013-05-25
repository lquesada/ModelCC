/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator.binaryoperators;

import test.languages.arithmeticcalculator.BinaryOperator;
import test.languages.arithmeticcalculator.Expression;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Priority(value=4)
@Pattern(regExp="\\*")
public class MultiplicationOperator extends BinaryOperator implements IModel {

    @Override
    public double eval(Expression e1, Expression e2) {
        return e1.eval()*e2.eval();
    }

}
