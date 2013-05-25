/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.lamb;

import java.io.Reader;
import java.io.Serializable;
import java.util.Set;
import org.modelcc.language.lexis.LexicalSpecification;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 * Lamb - Lexer with AMBiguity Support.
 * @author elezeta
 * @serial
 */
public final class Lamb implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Performs a lexical analysis.
     * @param ls the lexer specification.
     * @param ignore the list of ignore patterns.
     * @param input the input string.
     * @return the obtained lexical graph.
     */
    public LexicalGraph scan(LexicalSpecification ls,Set<PatternRecognizer> ignore,Reader input) {
        LambSafe lambs = new LambSafe();
        return lambs.scan(ls,ignore,input);
    }

}