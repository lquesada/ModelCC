/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.simplearithmeticexpression.binaryoperators;

import org.modelcc.*;
import org.modelcc.examples.language.simplearithmeticexpression.BinaryOperator;
import org.modelcc.examples.language.simplearithmeticexpression.Expression;

/**
 * @author elezeta
 */
@Priority(value=4)
@Pattern(regExp="\\/")
public class DivisionOperator extends BinaryOperator implements IModel {

    @Override
    public double eval(Expression e1, Expression e2) {
        return e1.eval()/e2.eval();
    }

}
