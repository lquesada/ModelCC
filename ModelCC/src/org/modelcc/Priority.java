/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Priority constraints.
 * @author elezeta
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {

    /**
     * @return the priority value
     */
  public int value() default 0;

    /**
     * @return the preceded classes
     */
  public Class[] precedes() default {};

  //Sets priority value. Lower values goes first.
  //Also defines precedence before specified classes.

}
