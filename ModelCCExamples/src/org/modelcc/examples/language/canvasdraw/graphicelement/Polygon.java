/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw.graphicelement;

import java.awt.Graphics;
import java.util.List;
import org.modelcc.*;
import org.modelcc.examples.language.canvasdraw.Coordinates;
import org.modelcc.examples.language.canvasdraw.GraphicElement;

/**
 *
 * @author elezeta
 */
@Prefix("polygon")
public class Polygon extends GraphicElement implements IModel {

    @Prefix("\\[")
    @Suffix("\\]")
    @Multiplicity(minimum=3)
    List<Coordinates> coords;
    
    @Optional
    Fill fill;
    
    @Override
    public void paint(Graphics g) {
        int x[] = new int[coords.size()];
        int y[] = new int[coords.size()];
        int n = coords.size();
        for (int i = 0;i < n;i++) {
            x[i] = coords.get(i).getX().getValue();
            y[i] = coords.get(i).getY().getValue();
        }
        if (fill != null)
            g.fillPolygon(x,y,n);
        else
            g.drawPolygon(x,y,n);
    }
}
