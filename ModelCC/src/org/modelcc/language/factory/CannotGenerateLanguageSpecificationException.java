/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;

/**
 * Cannot Generate Language Specification Exception
 * @author elezeta
 * @serial
 */
public final class CannotGenerateLanguageSpecificationException extends Exception implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Chained exception.
     */
    Exception e;
    
    /**
     * Constructor.
     * @param e the chained exception.
     */
    public CannotGenerateLanguageSpecificationException(Exception e) {
        this.e = e;
    }
    
    /**
     * Print stack trace.
     */
    @Override
    public void printStackTrace() {
        System.out.println(this.getClass().getName()+" -> "+e.getClass().getName());
        e.printStackTrace();
    }
    
}
