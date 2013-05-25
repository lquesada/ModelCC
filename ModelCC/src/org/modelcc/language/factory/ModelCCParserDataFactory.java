/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import org.modelcc.language.syntax.*;
import java.io.Serializable;


/**
 * ModelCC Parser Data Factory.
 * @author elezeta
 * @serial
 */
public final class ModelCCParserDataFactory extends ParserDataFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Generate a parser data object.
     * @return the parser data object.
     */
    public Object generate() {
        return new ModelCCParserData();
    }
    
}