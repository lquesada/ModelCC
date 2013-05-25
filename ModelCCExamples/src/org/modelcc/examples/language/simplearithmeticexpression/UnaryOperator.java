/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.simplearithmeticexpression;

import org.modelcc.*;

/**
 * @author elezeta
 */
public abstract class UnaryOperator implements IModel {

    public abstract double eval(Expression e);

}
