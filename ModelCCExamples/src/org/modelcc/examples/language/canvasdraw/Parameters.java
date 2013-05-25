/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@FreeOrder
public class Parameters implements IModel {
    @Prefix("width")
    @Optional
    private Numeric width;
    
    @Prefix("height")
    @Optional
    private Numeric height;
    
    @Prefix("background")
    @Optional
    private RGBColor bgcolor;

    public Numeric getWidth() {
        return width;
    }

    public Numeric getHeight() {
        return height;
    }

    public RGBColor getBackground() {
        return bgcolor;
    }

}
