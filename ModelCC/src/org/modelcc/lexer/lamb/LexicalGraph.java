/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.lamb;

import java.util.Collections;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * Lexical Graph.
 * @author elezeta
 * @serial
 */
public final class LexicalGraph implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * List of start tokens of this graph.
     */
    Set<Token> tokens;

    /**
     * List of start tokens of this graph.
     */
    Set<Token> start;

    /**
     * List of preceding tokens.
     */
    Map<Token,Set<Token>> preceding;

    /**
     * List of following tokens.
     */
    Map<Token,Set<Token>> following;

    /**
     * Input start index
     */
    int inputstart;

    /**
     * Input end index
     */
    int inputend;
    
    /**
     * Default constructor.
     * @param tokens the token set
     * @param start the start symbol
     * @param preceding the preceding map
     * @param following the following map
     */
    public LexicalGraph(Set<Token> tokens,Set<Token> start,Map<Token,Set<Token>> preceding,Map<Token,Set<Token>> following,int inputstart,int inputend) {
        this.tokens = tokens;
        this.start = start;
        this.preceding = preceding;
        this.following = following;
        this.inputstart = inputstart;
        this.inputend = inputend;
    }

    /**
     * @return the start tokens of this graph.
     */
    public Set<Token> getStart() {
        return Collections.unmodifiableSet(start);
    }

    /**
     * @return the tokens used in this graph.
     */
    public Set<Token> getTokens() {
        return Collections.unmodifiableSet(tokens);
    }

    /**
     * @return the map of precedings.
     */
    public Map<Token, Set<Token>> getPreceding() {
        return Collections.unmodifiableMap(preceding);
    }

    /**
     * @return the map of following.
     */
    public Map<Token, Set<Token>> getFollowing() {
        return Collections.unmodifiableMap(following);
    }

    /**
     * @return the input start index.
     */
    public int getInputStart() {
        return inputstart;
    }

    /**
     * @return the input end index.
     */
    public int getInputEnd() {
        return inputend;
    }

}
