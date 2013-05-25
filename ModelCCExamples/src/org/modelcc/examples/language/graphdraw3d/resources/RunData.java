/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.resources;

import java.util.HashMap;
import java.util.Map;
import org.modelcc.examples.language.graphdraw3d.ObjectName;

/**
 * Includes run data.
 * @author elezeta
 */
public final class RunData {

    private ColorData currentColor;
    private TextureData currentTexture;
    private Map<ObjectName,Integer> nestings;

    public RunData(ColorData currentColor,TextureData currentTexture) {
        this.currentColor = currentColor;
        this.currentTexture = currentTexture;
        this.nestings = new HashMap<ObjectName,Integer>();
    }
    
    /**
     * @return the currentColor
     */
    public ColorData getCurrentColor() {
        return currentColor;
    }

    /**
     * @param currentColor the currentColor to set
     */
    public void setCurrentColor(ColorData currentColor) {
        this.currentColor = currentColor;
        currentColor.draw();
    }

    /**
     * @return the currentColor
     */
    public TextureData getCurrentTexture() {
        return currentTexture;
    }

    /**
     * @param currentTexture the currentTexture to set
     */
    public void setCurrentTexture(TextureData currentTexture) {
        this.currentTexture = currentTexture;
    }

    public Integer getNesting(ObjectName name) {
        if (nestings.containsKey(name))
            return nestings.get(name);
        else
            return new Integer(0);
    }

    public void incNesting(ObjectName name) {
        if (nestings.containsKey(name))
            nestings.put(name,nestings.get(name)+1);
        else
            nestings.put(name,1);
    }

    public void decNesting(ObjectName name) {
        if (nestings.get(name)==1)
            nestings.remove(name);
        else
            nestings.put(name,nestings.get(name)-1);
    }
    
}
