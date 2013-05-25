/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.resources;


/**
 *
 * @author elezeta
 */
public class TextureData {
    
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    
    public TextureData(float x1,float y1,float x2,float y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    
    /**
     * @return the x1
     */
    public float getX1() {
        return x1;
    }

    /**
     * @return the y1
     */
    public float getY1() {
        return y1;
    }

    /**
     * @return the x2
     */
    public float getX2() {
        return x2;
    }

    /**
     * @return the y2
     */
    public float getY2() {
        return y2;
    }

}
