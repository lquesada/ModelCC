/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines that the element contents may be placed in free order.
 * @author elezeta
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FreeOrder {

    /**
     * @return whether if the element is able to be placed in free order or not.
     */
    public boolean value() default true;

    //Contents may be shuffled.

}
