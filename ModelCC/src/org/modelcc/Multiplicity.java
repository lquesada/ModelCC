/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Multiplicity constraints.
 * @author elezeta
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Multiplicity {

  //Minimum number of elements in a container field.

    /**
     * @return the minimum multiplicity value.
     */
    public int minimum() default -19977;

    //Maximum number of elements in a container field.

    /**
     * @return the minimum multiplicity value.
     */
    public int maximum() default -19977;

}
