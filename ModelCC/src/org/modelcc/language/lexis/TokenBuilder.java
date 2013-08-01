/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.lexis;

import java.io.Serializable;
import org.modelcc.lexer.lamb.Token;

/**
 * Token builder.
 * @author elezeta
 * @serial
 */
public abstract class TokenBuilder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Builds a token, filling its data, and validates it.
     * @param t token to be built.
     * @return true if the token is valid, false if not
     */
    public abstract boolean build(Token t);

}
