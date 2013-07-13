/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.objects;

import org.lwjgl.util.glu.Sphere;
import org.modelcc.examples.language.graphdraw3d.SceneObject;
import static org.lwjgl.opengl.GL11.*;
import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.resources.TextureData;

/**
 * Sphere.
 * @author elezeta
 */
@Pattern(regExp="sphere")
public final class SphereObject extends SceneObject implements IModel {

    @Override
    public void draw(RunData rd,int iter) {
        TextureData texin = rd.getCurrentTexture();
        Sphere s = new Sphere();
        s.draw(0.5f,10,10);
    }
    
	@Override
	public void undo(RunData rd, int iter) {
	}
    
}
