/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.composition;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class CondSentence1 extends Sentence implements IModel {

    @Prefix("if")
    public Expression e;

    public Sentence ifsentence;

    @Prefix("else")
    @Optional
    public Sentence elsesentence;
}
