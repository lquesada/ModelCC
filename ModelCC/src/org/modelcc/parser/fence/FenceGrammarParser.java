/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import org.modelcc.lexer.lamb.LexicalGraph;
import org.modelcc.language.syntax.Grammar;

/**
 * Fence Grammar Parser
 * @author elezeta
 * @serial
 */
public final class FenceGrammarParser implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Performs the parse of a lexical graph
     * @param g the grammar
     * @param lg the lexical graph
     * @return a parsed graph
     */
    public ParsedGraph parse(Grammar g,LexicalGraph lg) {
        FenceGrammarParserSafe fgps = new FenceGrammarParserSafe();
        return fgps.parse(g,lg);
    }

}
