/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.tools;

import java.io.Serializable;

/**
 * Ambiguous Element Definition Exception.
 * @author elezeta
 * @serial
 */
public final class AmbiguousElementDefinitionException extends Exception implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Default constructor.
     */
    public AmbiguousElementDefinitionException() {
        super();
    }

}
