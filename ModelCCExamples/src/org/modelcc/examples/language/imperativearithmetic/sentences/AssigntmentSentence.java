/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic.sentences;

import org.modelcc.*;
import org.modelcc.examples.language.imperativearithmetic.Expression;
import org.modelcc.examples.language.imperativearithmetic.Sentence;
import org.modelcc.examples.language.imperativearithmetic.Variable;

/**
 * @author elezeta
 */
public class AssigntmentSentence extends Sentence implements IModel {

    @Reference
    Variable var;
    
    @Prefix("=")
    Expression val;
    
    @Override
    public String run() {
        var.setValue(val.eval());
        return "";
    }

}
