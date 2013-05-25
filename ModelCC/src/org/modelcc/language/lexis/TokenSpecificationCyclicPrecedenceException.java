/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.lexis;

import java.io.Serializable;

/**
 * Cyclic Precedence Exception.
 * @author elezeta
 * @serial
 */
public final class TokenSpecificationCyclicPrecedenceException extends Exception implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Default constructor.
     * @param message Exception message.
     */
    public TokenSpecificationCyclicPrecedenceException(String message) {
        super(message);
    }

}
