/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.objects;

import org.modelcc.examples.language.graphdraw3d.SceneObject;
import static org.lwjgl.opengl.GL11.*;
import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.resources.TextureData;

/**
 * Square.
 * @author elezeta
 */
@Pattern(regExp="square")
public final class SquareObject extends SceneObject implements IModel {

    @Override
    public void draw(RunData rd,int iter) {
        TextureData texin = rd.getCurrentTexture();
        glBegin(GL_QUADS);
        glTexCoord2f(texin.getX2(),texin.getY2());
        glVertex3f(-0.5f,-0.5f,0.f);
        glTexCoord2f(texin.getX1(),texin.getY2());
        glVertex3f(-0.5f,+0.5f,0.f);
        glTexCoord2f(texin.getX1(),texin.getY1());
        glVertex3f(+0.5f,+0.5f,0.f);
        glTexCoord2f(texin.getX2(),texin.getY1());
        glVertex3f(+0.5f,-0.5f,0.f);

        glTexCoord2f(texin.getX1(),texin.getY1());
        glVertex3f(+0.5f,+0.5f,0.f);
        glTexCoord2f(texin.getX1(),texin.getY2());
        glVertex3f(-0.5f,+0.5f,0.f);
        glTexCoord2f(texin.getX2(),texin.getY2());
        glVertex3f(-0.5f,-0.5f,0.f);
        glTexCoord2f(texin.getX2(),texin.getY1());
        glVertex3f(+0.5f,-0.5f,0.f);
        
        glEnd();
    }
    
	@Override
	public void undo(RunData rd, int iter) {
	}
    
}
