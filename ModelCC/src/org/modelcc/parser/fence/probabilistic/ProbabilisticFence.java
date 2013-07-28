/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence.probabilistic;

import java.io.Serializable;
import org.modelcc.lexer.lamb.LexicalGraph;
import org.modelcc.language.syntax.SyntacticSpecification;
import org.modelcc.parser.fence.Fence;
import org.modelcc.parser.fence.FenceGrammarParser;
import org.modelcc.parser.fence.ParsedGraph;
import org.modelcc.parser.fence.SyntaxGraph;

/**
 * Fence - SyntaxGraphParser with Lexical and Syntactic Ambiguity Support.
 * @author elezeta
 * @serial
 */
public final class ProbabilisticFence extends Fence implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Perform probabilistic syntactical analysis on a Lexical Graph.
     * @param fs the Fence specification.
     * @param lg the input lexical graph.
     * @return a syntax graph.
     */
    public SyntaxGraph parse(SyntacticSpecification fs,LexicalGraph lg) {
        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(fs.getGrammar(),lg);
        ProbabilisticFenceConstraintEnforcer fce = new ProbabilisticFenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(fs.getConstraints(),pg);
        return sg;
    }

}