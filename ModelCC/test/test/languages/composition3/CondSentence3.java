/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.composition3;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Composition(CompositionType.LAZY)
public class CondSentence3 extends Sentence implements IModel {

    @Prefix("if")
    public Expression e;

    public Sentence ifsentence;

    @Prefix("else")
    @Optional
    public Sentence elsesentence;
}
