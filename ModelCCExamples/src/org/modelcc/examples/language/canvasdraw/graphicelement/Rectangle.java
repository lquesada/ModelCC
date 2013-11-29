/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw.graphicelement;

import org.modelcc.*;
import org.modelcc.examples.language.canvasdraw.Coordinates;
import org.modelcc.examples.language.canvasdraw.GraphicElement;
import java.awt.*;

/**
 *
 * @author elezeta
 */
@Prefix("rectangle")
public class Rectangle extends GraphicElement implements IModel {
    
	@Multiplicity(minimum=2,maximum=2)
    @Prefix("\\[")
    @Suffix("\\]")
    Coordinates[] coords;

    @Optional
    Fill fill;
   
    @Override
    public void paint(Graphics g) {
        int x1 = coords[0].getX().getValue();
        int y1 = coords[0].getY().getValue();
        int x2 = coords[1].getX().getValue();
        int y2 = coords[1].getY().getValue();
        int x,y,w,h;
        if (x1<x2) {
            x = x1;
            w = x2-x1;
        }
        else {
            x = x2;
            w = x1-x2;
        }
        if (y1<y2) {
            y = y1;
            h = y2-y1;
        }
        else {
            y = y2;
            h = y1-y2;
        }
        if (fill != null)
            g.fillRect(x,y,w,h);
        else
            g.drawRect(x,y,w,h);
    }
}
