/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.canvasdraw;

import org.modelcc.*;

import java.awt.*;
import javax.swing.JComponent;

/**
 *
 * @author elezeta
 */
@Prefix("canvas")
public class CanvasDraw extends JComponent implements IModel {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    public Parameters pars;
    
    @Multiplicity(minimum=0)
    public GraphicElement[] elems;    
    
    int width;
    int height;
    Color background;

    @Setup
    public void open() {
        width = 640;
        height = 480;
        background = new Color(255,255,255);
        if (pars.getBackground() != null)
            background = pars.getBackground().getColor();
        if (pars.getWidth() != null)
            width = pars.getWidth().getValue();
        if (pars.getHeight() != null)
            height = pars.getHeight().getValue();
        setSize(width,height);
        setBackground(background);
        setVisible(true);
        setDoubleBuffered(true);
    }
    @Override
    public void paintComponent(Graphics g) {
        g.setColor(background);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(new Color(0,0,0));
        ((Graphics2D)g).setStroke(new BasicStroke(1f));

        if (elems != null) {
            for (int i = 0;i < elems.length;i++) {
                elems[i].paint(g);
            }
        }
    }
    
}
