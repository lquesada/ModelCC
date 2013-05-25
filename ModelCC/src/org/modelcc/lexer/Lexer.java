/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import org.modelcc.lexer.lamb.LexicalGraph;

/**
 * ModelCC Lexer
 * @author elezeta
 * @serial
 */
public abstract class Lexer implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Scans an input reader.
     * @param input the input reader.
     * @return the obtained lexical graph.
     */
    public abstract LexicalGraph scan(Reader input);
    
    /**
     * Scans an input string.
     * @param input the input string.
     * @return the obtained lexical graph.
     */
    public LexicalGraph scan(String input) {
        return scan(new StringReader(input));
    }

}
