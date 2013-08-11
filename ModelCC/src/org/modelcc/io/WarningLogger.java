/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.io;

import java.io.Serializable;
import java.util.logging.Logger;

/**
 * Warning logger.
 * @author elezeta
 * @serial
 */
public class WarningLogger extends Logger implements Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    /**
     * Constructor.
     */
    public WarningLogger() {
        super(null,null);
    }
    
}
