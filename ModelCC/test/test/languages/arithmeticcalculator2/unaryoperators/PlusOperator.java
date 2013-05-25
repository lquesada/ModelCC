/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator2.unaryoperators;

import test.languages.arithmeticcalculator.Expression;
import test.languages.arithmeticcalculator.UnaryOperator;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Pattern(regExp="\\+")
public class PlusOperator extends UnaryOperator implements IModel {

    @Override
    public double eval(Expression e) {
        return e.eval();
    }

}
