/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Numeric implements IModel {
    @Value private int value;

    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }
}
