/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser;

import java.io.Serializable;

/**
 * Parser Exception
 * @author elezeta
 * @serial
 */
public class ParserException extends Exception implements Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Constructor.
     */
    public ParserException() {
    }

}
