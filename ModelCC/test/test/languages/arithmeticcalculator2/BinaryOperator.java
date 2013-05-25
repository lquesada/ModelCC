/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator2;

import org.modelcc.*;

/**
 * @author elezeta
 */
@Associativity(AssociativityType.NON_ASSOCIATIVE)
public abstract class BinaryOperator implements IModel {

    public abstract double eval(Expression2 e1,Expression2 e2);

}
