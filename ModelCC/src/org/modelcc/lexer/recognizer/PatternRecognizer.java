/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.recognizer;

import java.io.Serializable;

/**
 * Pattern recognizer interface.
 * @author elezeta
 * @serial
 */
public abstract class PatternRecognizer implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Argument.
     */
    String arg;
    
    /**
     * Constructor.
     * @param arg the argument.
     */
    public PatternRecognizer(String arg) {
        this.arg = arg;
    }

    /**
     * Try to match the pattern in a certain position of a char sequence.
     * @param cs the char sequence in which to match the pattern.
     * @param start the position of the char sequence in which to match the pattern.
     * @return an object that contains the matched subsequence if there was a match, null otherwise.
     */
    public abstract MatchedObject read(CharSequence cs,int start);

    /**
     * @return the argument.
     */
    public String getArg() {
        return arg;
    }
}