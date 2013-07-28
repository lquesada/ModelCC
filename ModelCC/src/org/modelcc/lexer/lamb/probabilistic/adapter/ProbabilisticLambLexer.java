/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.lamb.probabilistic.adapter;

import org.modelcc.lexer.lamb.*;
import org.modelcc.lexer.lamb.probabilistic.ProbabilisticLamb;

import java.io.Reader;
import java.io.Serializable;
import java.util.Set;
import org.modelcc.language.lexis.LexicalSpecification;
import org.modelcc.lexer.Lexer;
import org.modelcc.lexer.ProbabilisticLexer;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 * PLamb - Probabilistic Lexer with AMBiguity Support.
 * @author elezeta
 * @serial
 */
public final class ProbabilisticLambLexer extends ProbabilisticLexer implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * The probabilistic lamb lexer.
     */
    protected ProbabilisticLamb gl;

    /**
     * Lexical Specification.
     */
    protected LexicalSpecification ls;
    
    /**
     * Ignore pattern set.
     */
    protected Set<PatternRecognizer> ignore;

    /**
     * Constructor.
     * @param gl the lamb lexer.
     * @param ls the lexical specification.
     */
    public ProbabilisticLambLexer(LexicalSpecification ls,ProbabilisticLamb gl) {
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
    public ProbabilisticLambLexer(LexicalSpecification ls,Set<PatternRecognizer> ignore,ProbabilisticLamb gl) {
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