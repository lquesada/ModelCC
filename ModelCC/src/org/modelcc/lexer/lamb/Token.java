/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.lamb;

import java.io.Serializable;

/**
 * Token.
 * @author elezeta
 * @serial
 */
public final class Token implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Token type.
     */
    private Object type;

    /**
     * Value.
     */
    private Object value;

    /**
     * Start index.
     */
    private int startIndex;

    /**
     * End index.
     */
    private int endIndex;

    /**
     * User data.
     */
    private Object userData;

    /**
     * matched text string.
     */
    private CharSequence string;
    
    /**
     * Constructor.
     * @param type the token type.
     * @param value the value.
     * @param startIndex the startIndex index.
     * @param endIndex the endIndex index.
     * @param string the matched string. 
     */
    public Token(Object type,Object value,int startIndex,int endIndex,CharSequence string) {
        this.type = type;
        this.value = value;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.string = string;
    }

    /**
     * @return the token type.
     */
    public Object getType() {
        return type;
    }

    /**
     * @return the value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * @return the startIndex index.
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * @return the endIndex index.
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * @return the userData
     */
    public Object getUserData() {
        return userData;
    }

    /**
     * @param userData the userData to set
     */
    public void setUserData(Object userData) {
        this.userData = userData;
    }

    /**
     * @return the string
     */
    public CharSequence getString() {
        return string;
    }

}
