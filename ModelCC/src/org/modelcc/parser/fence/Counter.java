/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;

/**
 * Referenceable counter.
 * @author elezeta
  * @serial
 */
public class Counter implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    
    public int val = 0;
    
}
