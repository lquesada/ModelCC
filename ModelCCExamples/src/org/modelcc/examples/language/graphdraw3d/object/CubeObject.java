/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.object;

import org.modelcc.examples.language.graphdraw3d.SceneObject;
import static org.lwjgl.opengl.GL11.*;
import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.resources.TextureData;

/**
 * Axis.
 * @author elezeta
 */
@Pattern(regExp="cube")
public final class CubeObject extends SceneObject implements IModel {

    @Override
    public void draw(RunData rd) {
        TextureData texin = rd.getCurrentTexture();

        glBegin(GL_QUADS);
        //back
        glTexCoord2f(texin.getX2(),texin.getY2());
        glVertex3f(-0.5f,-0.5f,-0.5f);
        glTexCoord2f(texin.getX1(),texin.getY2());
        glVertex3f(-0.5f,+0.5f,-0.5f);
        glTexCoord2f(texin.getX1(),texin.getY1());
        glVertex3f(+0.5f,+0.5f,-0.5f);
        glTexCoord2f(texin.getX2(),texin.getY1());
        glVertex3f(+0.5f,-0.5f,-0.5f);

        //front
        glTexCoord2f(texin.getX2(),texin.getY2());
        glVertex3f(+0.5f,+0.5f,+0.5f);
        glTexCoord2f(texin.getX1(),texin.getY2());
        glVertex3f(-0.5f,+0.5f,+0.5f);
        glTexCoord2f(texin.getX1(),texin.getY1());
        glVertex3f(-0.5f,-0.5f,+0.5f);
        glTexCoord2f(texin.getX2(),texin.getY1());
        glVertex3f(+0.5f,-0.5f,+0.5f);

        //right
        glTexCoord2f(texin.getX2(),texin.getY2());
        glVertex3f(+0.5f,-0.5f,-0.5f);
        glTexCoord2f(texin.getX1(),texin.getY2());
        glVertex3f(+0.5f,+0.5f,-0.5f);
        glTexCoord2f(texin.getX1(),texin.getY1());
        glVertex3f(+0.5f,+0.5f,+0.5f);
        glTexCoord2f(texin.getX2(),texin.getY1());
        glVertex3f(+0.5f,-0.5f,+0.5f);

        //left
        glTexCoord2f(texin.getX2(),texin.getY2());
        glVertex3f(-0.5f,+0.5f,+0.5f);
        glTexCoord2f(texin.getX1(),texin.getY2());
        glVertex3f(-0.5f,+0.5f,-0.5f);
        glTexCoord2f(texin.getX1(),texin.getY1());
        glVertex3f(-0.5f,-0.5f,-0.5f);
        glTexCoord2f(texin.getX2(),texin.getY1());
        glVertex3f(-0.5f,-0.5f,+0.5f);

        //bottom
        glTexCoord2f(texin.getX2(),texin.getY2());
        glVertex3f(-0.5f,-0.5f,-0.5f);
        glTexCoord2f(texin.getX1(),texin.getY2());
        glVertex3f(+0.5f,-0.5f,-0.5f);
        glTexCoord2f(texin.getX1(),texin.getY1());
        glVertex3f(+0.5f,-0.5f,+0.5f);
        glTexCoord2f(texin.getX2(),texin.getY1());
        glVertex3f(-0.5f,-0.5f,+0.5f);

        //top
        glTexCoord2f(texin.getX2(),texin.getY2());
        glVertex3f(+0.5f,+0.5f,+0.5f);
        glTexCoord2f(texin.getX1(),texin.getY2());
        glVertex3f(+0.5f,+0.5f,-0.5f);
        glTexCoord2f(texin.getX1(),texin.getY1());
        glVertex3f(-0.5f,+0.5f,-0.5f);
        glTexCoord2f(texin.getX2(),texin.getY1());
        glVertex3f(-0.5f,+0.5f,+0.5f);

        glEnd();
    }
    
    
}
