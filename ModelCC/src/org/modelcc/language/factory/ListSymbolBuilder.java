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
 * Symbol list builder
 * @author elezeta
 * @serial
 */
public final class ListSymbolBuilder extends SymbolBuilder implements Serializable {

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
        ListContents restContents = (ListContents) t.getContents().get(t.getContents().size()-1).getUserData();
        Object[] rest = restContents.getL();
        Object[] l = new Object[rest.length+1];
        l[0] = t.getContents().get(0).getUserData();
        for (int i = 0;i < rest.length;i++)
            l[i+1] = rest[i];
        t.setUserData(new ListContents(l,restContents.getExtra()));;
        return true;
    }
}
