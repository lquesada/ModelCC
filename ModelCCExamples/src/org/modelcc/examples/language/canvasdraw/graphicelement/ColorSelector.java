/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw.graphicelement;

import org.modelcc.*;
import java.awt.*;
import org.modelcc.examples.language.canvasdraw.GraphicElement;
import org.modelcc.examples.language.canvasdraw.RGBColor;

/**
 *
 * @author elezeta
 */
public class ColorSelector extends GraphicElement implements IModel {
    @Prefix("color")
    RGBColor color;

    @Override
    public void paint(Graphics g) {
        g.setColor(color.getColor());
    }
}
