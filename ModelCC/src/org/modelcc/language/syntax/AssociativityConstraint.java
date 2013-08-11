/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;

/**
 * AssociativityConstraint.
 * @author elezeta
 */
public enum AssociativityConstraint implements Serializable {

    /**
     * Left to right associativity.
     */
    LEFT_TO_RIGHT,

    /**
     * Right to left associativity.
     */
  RIGHT_TO_LEFT,

    /**
     * Undefined associativity.
     */
  UNDEFINED,

    /**
     * Non associative.
     */
  NON_ASSOCIATIVE
          
}
