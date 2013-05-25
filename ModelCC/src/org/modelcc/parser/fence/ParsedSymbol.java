/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import java.util.Set;

/**
 * Parsed symbol.
 * @author elezeta
 * @serial
 */
public final class ParsedSymbol implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Type.
     */
    private Object type;

    /**
     * Start index.
     */
    private int startIndex;

    /**
     * Start index.
     */
    private int endIndex;

    /**
     * User data.
     */
    private Object userData;

    /**
     * Single type history
     */
    private Set<Object> singleTypeHistory;
    
    /**
     * Is token?
     */
    private boolean token;
    
    /**
     * Text.
     */
    private CharSequence string;
    
    /**
     * Constructor.
     * @param type the type of the token.
     * @param startIndex the start index.
     * @param endIndex the end index.
     */
    public ParsedSymbol(Object type,int startIndex,int endIndex) {
        this.type = type;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.singleTypeHistory = null;
        this.string = null;
        this.token = false;
    }

    /**
     * Constructor.
     * @param type the type of the token.
     * @param startIndex the start index.
     * @param endIndex the end index.
     * @param string the token text.
     */
    public ParsedSymbol(Object type,int startIndex,int endIndex,CharSequence string) {
        this.type = type;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.singleTypeHistory = null;
        this.string = string;
        this.token = true;
    }

    /**
     * Constructor.
     * @param type the type of the token.
     * @param startIndex the start index.
     * @param endIndex the end index.
     * @param singleTypeHistory the single type history.
     */
    public ParsedSymbol(Object type,int startIndex,int endIndex,Set<Object> singleTypeHistory) {
        this.type = type;
        this.userData = null;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.singleTypeHistory = singleTypeHistory;
        this.token = false;
    }

    /**
     * Whether if the syntactic symbol is a token or not.
     * @return true if it is a token, false if not
     */
    public boolean isToken() {
        return token;
    }

    /**
     * @return the type
     */
    public Object getType() {
        return type;
    }

    /**
     * @return the start index
     */
    public int getStartIndex() {
        return startIndex;
    }

    /**
     * @return the end index
     */
    public int getEndIndex() {
        return endIndex;
    }

    /**
     * Equals method
     * @param obj Object to compare with
     * @return true if equals, false if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ParsedSymbol other = (ParsedSymbol) obj;
        if (this.type != other.type && (this.type == null || !this.type.equals(other.type))) {
            return false;
        }
        if (this.startIndex != other.startIndex) {
            return false;
        }
        if (this.endIndex != other.endIndex) {
            return false;
        }
        return true;
    }

    /**
     * Unique hashCode
     * @return a hashCode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 59 * hash + this.startIndex;
        hash = 59 * hash + this.endIndex;
        return hash;
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
     * @return the singleTypeHistory
     */
    Set<Object> getSingleTypeHistory() {
        return singleTypeHistory;
    }

    /**
     * @param singleTypeHistory the singleTypeHistory to set
     */
    void setSingleTypeHistory(Set<Object> singleTypeHistory) {
        this.singleTypeHistory = singleTypeHistory;
    }

    /**
     * @return the string
     */
    public CharSequence getString() {
        return string;
    }


}
