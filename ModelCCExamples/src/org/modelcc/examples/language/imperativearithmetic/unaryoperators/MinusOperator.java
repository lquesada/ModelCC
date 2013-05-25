/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic.unaryoperators;

import org.modelcc.examples.language.imperativearithmetic.Expression;
import org.modelcc.examples.language.imperativearithmetic.UnaryOperator;
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
