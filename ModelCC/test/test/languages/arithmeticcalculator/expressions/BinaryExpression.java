/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.arithmeticcalculator.expressions;

import test.languages.arithmeticcalculator.BinaryOperator;
import test.languages.arithmeticcalculator.Expression;
import org.modelcc.*;
import org.modelcc.probabilistic.NumericProbabilityEvaluator;
import org.modelcc.probabilistic.Probability;

/**
 * @author elezeta
 */
public class BinaryExpression extends Expression implements IModel {
	
	@Probability(evaluator=NumericProbabilityEvaluator.class,args="0.5")
    public Expression e1;
    public BinaryOperator op;
    
    @Position(element="op",position=Position.AFTER)
	@Probability(evaluator=NumericProbabilityEvaluator.class,args="0.8")
    public Expression e2;

    @Override
    public double eval() {
        return op.eval(e1,e2);
    }
}
