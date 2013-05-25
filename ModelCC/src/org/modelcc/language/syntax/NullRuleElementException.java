/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;

/**
 * Null Element Exception
 * @author elezeta
 * @serial
 */
public class NullRuleElementException extends Exception implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Default constructor.
     */
    public NullRuleElementException() {
        super();
    }

}
