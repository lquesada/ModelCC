/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Position constraint.
 * @author elezeta
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Position {

	  public static int BEFORE = 0;
	  public static int AFTER = 1;
	  public static int WITHIN = 2;
	  public static int EXTREME = 3;
	  public static int BEFORELAST = 4;
	  public static int AROUND = 5;
	  public static int ANY = 6;

	  public int position();

	  public String element();
  
	  public SeparatorPolicy separatorPolicy() default SeparatorPolicy.AFTER;
	  
}
