/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;
import org.modelcc.parser.fence.Symbol;

/**
 * Symbol builder.
 * @author elezeta
 * @serial
 */
public abstract class PostSymbolBuilder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Builds a symbol, filling its data, and validates it.
     * @param t symbol to be built.
     * @param data parser data.
     * @param usedIn where the symbol is used in.
     * @return true if the symbol is valid, false if not
     */
    public abstract boolean build(Symbol t,Object data,Map<Symbol,Set<Symbol>> usedIn);

}
