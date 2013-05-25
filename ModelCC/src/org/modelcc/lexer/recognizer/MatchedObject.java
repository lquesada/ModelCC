/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.recognizer;

import java.io.Serializable;

/**
 * A matched object as obtained by a pattern recognizer.
 * @author elezeta
 * @serial
 */
public final class MatchedObject implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Matched object.
     */
    private Object object;

    /**
     * Matched text.
     */
    private String text;

    /**
     * Default constructor.
     * @param object the matched object.
     * @param text the matched text.
     */
    public MatchedObject(Object object,String text) {
        this.object = object;
        this.text = text;
    }

    /**
     * @return the object
     */
    public Object getObject() {
        return object;
    }

    /**
     * @return the length
     */
    public String getText() {
        return text;
    }
    
}
