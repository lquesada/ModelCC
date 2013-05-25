/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Separator(",")
public class Coordinates implements IModel {
    
    @Prefix("\\(")
    private Numeric x;
    
    @Prefix(",")
    @Suffix("\\)")
    private Numeric y;

    /**
     * @return the x
     */
    public Numeric getX() {
        return x;
    }

    /**
     * @return the y
     */
    public Numeric getY() {
        return y;
    }
    
}
