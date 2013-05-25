/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.resources;

import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author elezeta
 */
public class ColorData {

    private double r;
    private double g;
    private double b;
    private double a;

    public ColorData(double r,double g,double b,double a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;

        if (r<0.0)
            r = 0.0;
        if (r>1.0)
            r = 1.0;
        if (g<0.0)
            g = 0.0;
        if (g>1.0)
            g = 1.0;
        if (b<0.0)
            b = 0.0;
        if (b>1.0)
            b = 1.0;
        if (a<0.0)
            a = 0.0;
        if (a>1.0)
            a = 1.0;
    }

    /**
     * @return the r
     */
    public double getR() {
        return r;
    }

    /**
     * @return the g
     */
    public double getG() {
        return g;
    }

    /**
     * @return the b
     */
    public double getB() {
        return b;
    }

    /**
     * @return the a
     */
    public double getA() {
        return a;
    }

    public void draw() {
        glColor4d(r,g,b,a);
    }
    
}
