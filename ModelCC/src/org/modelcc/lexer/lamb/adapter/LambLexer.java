/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.lamb.adapter;

import org.modelcc.lexer.lamb.*;
import java.io.Reader;
import java.io.Serializable;
import java.util.Set;
import org.modelcc.language.lexis.LexicalSpecification;
import org.modelcc.lexer.Lexer;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 * Lamb - Lexer with AMBiguity Support.
 * @author elezeta
 * @serial
 */
public class LambLexer extends Lexer implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * The lamb lexer.
     */
    protected Lamb gl;

    /**
     * Lexical Specification.
     */
    protected LexicalSpecification ls;
    
    /**
     * Ignore pattern set.
     */
    protected Set<PatternRecognizer> ignore;

    /**
     * Protected constructor.
     */
    protected LambLexer() {
    }
    
    /**
     * Constructor.
     * @param gl the lamb lexer.
     * @param ls the lexical specification.
     */
    public LambLexer(LexicalSpecification ls,Lamb gl) {
        this.ls = ls;
        this.gl = gl;
        this.ignore = null;
    }
    
    /**
     * Constructor.
     * @param ls the lexical specification.
     * @param gl the lamb lexer.
     * @param ignore the ignore pattern set.
     */
    public LambLexer(LexicalSpecification ls,Set<PatternRecognizer> ignore,Lamb gl) {
        this.ls = ls;
        this.gl = gl;
        this.ignore = ignore;
    }
    
    /**
     * Scans an input string.
     * @param input the input string.
     * @return the obtained lexical graph.
     */    
    @Override
    public LexicalGraph scan(Reader input) {
        LexicalGraph sg = gl.scan(ls,ignore,input);
        return sg;
    }

}