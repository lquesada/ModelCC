/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator2;

import org.modelcc.*;

/**
 * @author elezeta
 */
public abstract class UnaryOperator implements IModel {

    public abstract double eval(Expression2 e);

}
