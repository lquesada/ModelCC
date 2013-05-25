/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.modelcc.language.syntax.SymbolBuilder;
import org.modelcc.parser.fence.Symbol;

/**
 * List zero symbol builder
 * @author elezeta
 * @serial
 */
public final class ListZeroSymbolBuilder extends SymbolBuilder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Builds a symbol, filling its data, and validates it.
     * @param t symbol to be built.
     * @param data the parser data.
     * @return true if the symbol is valid, false if not
     */
    public boolean build(Symbol t,Object data) {
        t.setUserData(new Object[0]);
        return true;
    }
}
