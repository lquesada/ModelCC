/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

/**
 * Syntax graph.
 * @author elezeta
 * @serial
 */
public final class SyntaxGraph implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Set of symbols used in this graph.
     */
    private Set<Symbol> symbols;

    /**
     * Set of roots symbols of this graph.
     */
    private Set<Symbol> roots;

    /**
     * Default constructor.
     * @param symbols the symbols used in this graph.
     * @param roots the roots symbols of this graph.
     */
    public SyntaxGraph(Set<Symbol> symbols,Set<Symbol> roots) {
        this.symbols = symbols;
        this.roots = roots;
    }

    /**
     * @return the roots symbols of this graph.
     */
    public Set<Symbol> getRoots() {
        return Collections.unmodifiableSet(roots);
    }

    /**
     * @return the symbols used in this graph.
     */
    public Set<Symbol> getSymbols() {
        return Collections.unmodifiableSet(symbols);
    }

}
