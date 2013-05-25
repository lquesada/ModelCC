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
@Prefix("line")
public class Line extends GraphicElement implements IModel {
    
    @Minimum(2)
    @Maximum(2)
    @Prefix("\\[")
    @Suffix("\\]")
    Coordinates[] coords;

    @Override
    public void paint(Graphics g) {
        int x1 = coords[0].getX().getValue();
        int y1 = coords[0].getY().getValue();
        int x2 = coords[1].getX().getValue();
        int y2 = coords[1].getY().getValue();
        g.drawLine(x1,y1,x2,y2);
    }
}
