/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator2.unaryoperators;

import test.languages.arithmeticcalculator2.Expression2;
import test.languages.arithmeticcalculator2.UnaryOperator;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Pattern(regExp="\\+")
public class PlusOperator extends UnaryOperator implements IModel {

    @Override
    public double eval(Expression2 e) {
        return e.eval();
    }

}
