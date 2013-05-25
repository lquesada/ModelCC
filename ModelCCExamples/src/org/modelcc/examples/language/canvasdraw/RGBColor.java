/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw;

import org.modelcc.*;
import java.awt.*;

/**
 *
 * @author elezeta
 */
@Prefix("\\(")
@Suffix("\\)")
public class RGBColor implements IModel {

    private Numeric red;
    
    @Prefix(",")
    private Numeric green;
    
    @Prefix(",")
    private Numeric blue;


    Color color;
    
    public Color getColor() {
        return color;
    }
    
    @Autorun
    public void load() {
        color = new Color(red.getValue(),green.getValue(),blue.getValue());
    }
}
