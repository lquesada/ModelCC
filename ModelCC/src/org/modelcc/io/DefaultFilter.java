/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.io;

import java.io.Serializable;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

/**
 * A filter that filters everything.
 * @author elezeta
 * @serial
 */
public final class DefaultFilter implements Filter,Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Override
	public boolean isLoggable(LogRecord record) {
        return true;
    }

}
