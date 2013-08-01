/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelcc.language.syntax.SymbolBuilder;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.parser.fence.Symbol;

/**
 * Symbol inherit builder
 * @author elezeta
 * @serial
 */
public final class DecoratorSymbolBuilder extends SymbolBuilder implements Serializable {

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
    @Override
	public boolean build(Symbol t,Object data) {
        ElementId eid = (ElementId)t.getType();
        eid.getElement();
        Object o = null;
        boolean valid = true;
        int target = -1;
        for (int i = 0;i < t.getContents().size();i++) {
            if (t.getContents().get(i).getType().getClass().equals(ElementId.class))
                target = i;
        }

        try {
            if (target != -1) {
                o = t.getContents().get(target).getUserData();
            }

            t.setUserData(o);

        } catch (Exception ex) {
            Logger.getLogger(DecoratorSymbolBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        return valid;
    }
}
