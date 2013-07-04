/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator.expressions;

import test.languages.arithmeticcalculator.BinaryOperator;
import test.languages.arithmeticcalculator.Expression;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class BinaryExpression extends Expression implements IModel {
    public Expression e1;
    public BinaryOperator op;
    
    @Position(element="op",position=Position.AFTER)
    public Expression e2;

    @Override
    public double eval() {
        return op.eval(e1,e2);
    }
}
