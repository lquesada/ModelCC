/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic.binaryoperators;

import org.modelcc.*;
import org.modelcc.examples.language.imperativearithmetic.BinaryOperator;
import org.modelcc.examples.language.imperativearithmetic.Expression;

/**
 * @author elezeta
 */
@Priority(value=5)
@Pattern(regExp="\\+")
public class AdditionOperator extends BinaryOperator implements IModel {

    @Override
    public double eval(Expression e1, Expression e2) {
        return e1.eval()+e2.eval();
    }

}
