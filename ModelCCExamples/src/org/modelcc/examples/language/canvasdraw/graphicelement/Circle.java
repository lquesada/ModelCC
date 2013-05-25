/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw.graphicelement;

import org.modelcc.*;
import org.modelcc.examples.language.canvasdraw.Coordinates;
import org.modelcc.examples.language.canvasdraw.GraphicElement;
import org.modelcc.examples.language.canvasdraw.Numeric;
import java.awt.*;

/**
 *
 * @author elezeta
 */
@Prefix("circle")
public class Circle extends GraphicElement implements IModel {

    Coordinates coords;
    
    @Prefix(",")
    Numeric radius;

    @Optional
    Fill fill;
    
    @Override
    public void paint(Graphics g) {
        int x = coords.getX().getValue();
        int y = coords.getY().getValue();
        int r = radius.getValue();
        if (fill != null)
            g.fillOval(x-r,y-r,r*2,r*2);
        else
            g.drawOval(x-r,y-r,r*2,r*2);
    }
}
