/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.io.java;

import java.io.Serializable;

/**
 * Class does not extend IModel.
 * @author elezeta
 * @serial
 */
public final class ClassDoesNotExtendIModelException extends Exception implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Constructor
     * @param string the message string
     */
    public ClassDoesNotExtendIModelException(String string) {
        super(string);
    }

}
