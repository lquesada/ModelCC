/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;


/**
 * Parser Data Factory.
 * @author elezeta
 * @serial
 */
public abstract class ParserDataFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Generate a parser data object.
     * @return the parser data object.
     */
    public abstract Object generate();
    
}