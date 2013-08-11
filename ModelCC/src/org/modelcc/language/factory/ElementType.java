/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;

/**
 *
 * @author elezeta
 * @serial
 */
public enum ElementType implements Serializable {

    /**
     * Element type.
     */
    ELEMENT,

    /**
     * Pattern element.
     */
    BASIC,

    /**
     * List element.
     */
    LIST,

    /**
     * List with before last element.
     */
    LISTBEFORELAST,

    /**
     * List with within element.
     */
    LISTWITHIN,

    /**
     * Zero list element.
     */
    LISTZERO,

    /**
     * List sep
     */
    LISTS,
    
    /**
     * List w
     */
    LISTW

}
