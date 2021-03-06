/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.io.Serializable;

/**
 * Associativity type.
 * @author elezeta
 * @serial
 */
public enum AssociativityType implements Serializable {

    /**
     * Undefined associativity.
     */
  UNDEFINED,

  /**
   * Left to right associativity.
   */
  LEFT_TO_RIGHT,

  /**
   * Right to left associativity.
   */
  RIGHT_TO_LEFT,

  /**
   * Non associative.
   */
  NON_ASSOCIATIVE
          
}
