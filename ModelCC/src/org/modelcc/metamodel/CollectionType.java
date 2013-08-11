/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import java.io.Serializable;

/**
 * Collection type.
 * @author elezeta
 * @serial
 */
public enum CollectionType implements Serializable {

    /**
     * List collection.
     */
  LIST,

    /**
    * Set collection.
    */
  SET,

    /**
     * Array collection.
     */
  LANGARRAY

}
