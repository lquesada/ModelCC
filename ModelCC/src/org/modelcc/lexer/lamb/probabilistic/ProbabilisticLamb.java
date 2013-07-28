/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.lamb.probabilistic;

import java.io.Reader;
import java.io.Serializable;
import java.util.Set;
import org.modelcc.language.lexis.LexicalSpecification;
import org.modelcc.lexer.lamb.Lamb;
import org.modelcc.lexer.lamb.LexicalGraph;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 * PLamb - Probabilistic Lexer with AMBiguity Support.
 * @author elezeta
 * @serial
 */
public final class ProbabilisticLamb extends Lamb implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Performs a probabilistic lexical analysis.
     * @param ls the lexer specification.
     * @param ignore the list of ignore patterns.
     * @param input the input string.
     * @return the obtained lexical graph.
     */
    @Override
    public LexicalGraph scan(LexicalSpecification ls,Set<PatternRecognizer> ignore,Reader input) {
        ProbabilisticLambSafe lambs = new ProbabilisticLambSafe();
        return lambs.scan(ls,ignore,input);
    }

}