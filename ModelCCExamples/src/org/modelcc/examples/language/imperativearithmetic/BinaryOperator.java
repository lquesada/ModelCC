/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic;

import org.modelcc.*;

/**
 * @author elezeta
 */
@Associativity(AssociativityType.LEFT_TO_RIGHT)
public abstract class BinaryOperator implements IModel {

    public abstract double eval(Expression e1,Expression e2);

}
