/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.io;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

/**
 * Warning export handler.
 * @author elezeta
 * @serial
 */
public final class WarningExportHandler extends Handler {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * The message list.
     */
    private List<String> messages;
    
    public WarningExportHandler() {
        super();
        setLevel(Level.ALL);
        this.messages = new ArrayList<String>();
    }
    
    @Override
    public void publish(LogRecord record) {
        messages.add(record.getMessage());
    }

    @Override
    public void flush() {
    }

    @Override
    public void close() throws SecurityException {
        messages = null;
    }

    /**
     * @return the messages
     */
    public List<String> getMessages() {
        return messages;
    }

}
