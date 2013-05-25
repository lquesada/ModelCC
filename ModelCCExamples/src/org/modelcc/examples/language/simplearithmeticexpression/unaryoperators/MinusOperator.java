/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.simplearithmeticexpression.unaryoperators;

import org.modelcc.examples.language.simplearithmeticexpression.Expression;
import org.modelcc.examples.language.simplearithmeticexpression.UnaryOperator;
import org.modelcc.*;

/**
 * @author elezeta
 */
@Pattern(regExp="-")
public class MinusOperator extends UnaryOperator implements IModel {

    @Override
    public double eval(Expression e) {
        return -e.eval();
    }

}
