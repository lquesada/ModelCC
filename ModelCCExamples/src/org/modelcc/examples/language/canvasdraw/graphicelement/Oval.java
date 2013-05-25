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
@Prefix("oval")
public class Oval extends GraphicElement implements IModel {

    @Minimum(2)
    @Maximum(2)
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
        int aux;
        if (x1>x2) {
            aux=x1;
            x1=x2;
            x2=aux;
        }
        if (y1>y2) {
            aux=y1;
            y1=y2;
            y2=aux;
        }
        if (fill != null)
            g.fillOval(x1,y1,x2-x1,y2-y1);
        else
            g.drawOval(x1,y1,x2-x1,y2-y1);
    }
}
