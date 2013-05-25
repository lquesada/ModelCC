/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Composition constraints.
 * @author elezeta
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Composition {

    /**
     * @return the composition constraint value.
     */
  public CompositionType value();

  //Defines Composition:
  //CompositionType.UNDEFINED
  //CompositionType.EAGER
  //CompositionType.LAZY
  //CompositionType.EXPLICIT

}