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
     * Chained exception.
     */
    Exception e;

    /**
     * Constructor.
     * @param e the chained exception.
     */
    public ParserException(Exception e) {
        this.e = e;
    }

    /**
     * Print stack trace.
     */
    @Override
    public void printStackTrace() {
        System.out.println(this.getClass().getName()+" -> "+e.getClass().getName()+": "+e.getMessage());
        e.printStackTrace();
    }

}
