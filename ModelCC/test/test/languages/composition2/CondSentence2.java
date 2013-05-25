/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.composition2;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Composition(CompositionType.EAGER)
public class CondSentence2 extends Sentence implements IModel {

    @Prefix("if")
    public Expression e;

    public Sentence ifsentence;

    @Prefix("else")
    @Optional
    public Sentence elsesentence;
}
