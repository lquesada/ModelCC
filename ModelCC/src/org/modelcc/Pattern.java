/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Pattern recognizer
 * @author elezeta
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Pattern {

    /**
     * @return the matcher class.
     */
  public Class matcher() default Pattern.class;

  /**
   * @return the matcher class arguments.
   */
  public String args() default "";

  /**
   * @return the regular expression.
   */
  public String regExp() default "]]]]]]]]]]]]]";

  //Define Pattern as class plus argument (matcher, args), or as RegExp

}
