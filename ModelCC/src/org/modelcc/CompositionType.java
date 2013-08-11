/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.io.Serializable;

/**
 * Composition type.
 * @author elezeta
 * @serial
 */
public enum CompositionType implements Serializable {

    /**
     * Undefined composition constraint
     */
  UNDEFINED,

    /**
     * Eager composition
     */
  EAGER,

    /**
     * Lazy composition
     */
  LAZY,

    /**
     * Explicit composition
     */
  EXPLICIT

}
