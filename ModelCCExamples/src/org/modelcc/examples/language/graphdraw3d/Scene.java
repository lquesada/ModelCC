/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.examples.language.graphdraw3d.resources.TextureData;
import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.resources.ColorData;
import org.modelcc.examples.language.graphdraw3d.resources.Resources;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;

/**
 * 3D Graph scene.
 * @author elezeta
 */
public final class Scene implements IModel {
    
    @Optional
    Definition[] definitions;
            
    @Prefix("scene")
    Sentence sceneContent;
    
    public void draw() {
        ColorData black = new ColorData(0.5,0.5,0.5,0.5);
        TextureData grass = Resources.getTextures()[0];
        black.draw();
        RunData rd = new RunData(black,grass);
        sceneContent.run(rd);
    }
    
}
