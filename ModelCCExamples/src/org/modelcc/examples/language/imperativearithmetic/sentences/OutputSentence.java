/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic.sentences;

import org.modelcc.*;
import org.modelcc.examples.language.imperativearithmetic.Expression;
import org.modelcc.examples.language.imperativearithmetic.Sentence;

/**
 * @author elezeta
 */
public class OutputSentence extends Sentence implements IModel {

    @Prefix("output")
    Expression exp;
    
    @Override
    public String run() {
        return new Double(exp.eval()).toString()+"\n";
        
    }

}
