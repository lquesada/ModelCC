/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic;

import java.util.List;
import java.util.Set;
import org.modelcc.*;

/**
 * @author elezeta
 */
public class ImperativeArithmetic implements IModel {

    @Prefix("variables")
    @Optional
    private Set<Variable> vars; 

    @Prefix("sentences")
    @Optional
    private List<Sentence> sentences;
    
    public String run() {
        String ret = "";
        if (sentences != null)
            for (int i = 0;i < sentences.size();i++)
                ret += sentences.get(i).run();
        return ret;
    }
}
