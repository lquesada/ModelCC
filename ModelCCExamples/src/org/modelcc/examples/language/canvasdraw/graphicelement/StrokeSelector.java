/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw.graphicelement;

import org.modelcc.*;
import java.awt.*;
import org.modelcc.examples.language.canvasdraw.GraphicElement;
import org.modelcc.examples.language.canvasdraw.Numeric;

/**
 *
 * @author elezeta
 */
public class StrokeSelector extends GraphicElement implements IModel {
    @Prefix("stroke")
    Numeric stroke;

    @Override
    public void paint(Graphics g) {
        ((Graphics2D)g).setStroke(new BasicStroke((float)stroke.getValue()));
    }
}
