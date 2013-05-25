/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Associativity constraints.
 * @author elezeta
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Associativity {
    /**
     * @return the associativity value
     */
    public AssociativityType value();

  //Defines Associativity:
  //AssociativityType.UNDEFINED
  //AssociativityType.LEFT_TO_RIGHT
  //AssociativityType.RIGHT_TO_LEFT
  //AssociativityType.NON_ASSOCIATIVE

}
