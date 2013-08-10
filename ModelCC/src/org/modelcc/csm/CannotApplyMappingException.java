/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm;

import java.io.Serializable;

/**
 * Cannot Generate Language Specification Exception
 * @author elezeta
 * @serial
 */
public final class CannotApplyMappingException extends Exception implements Serializable {

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
    public CannotApplyMappingException(Exception e) {
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
