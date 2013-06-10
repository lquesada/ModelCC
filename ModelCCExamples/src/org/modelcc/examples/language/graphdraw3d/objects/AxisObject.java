/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.objects;

import org.modelcc.examples.language.graphdraw3d.SceneObject;
import static org.lwjgl.opengl.GL11.*;
import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;

/**
 * AxisObject.
 * @author elezeta
 */
@Pattern(regExp="axis")
public final class AxisObject extends SceneObject implements IModel {
    
    @Override
    public void draw(RunData rd) {
        
        glColor4d(1,0,0,1); // X axis is red.
        glBegin(GL_QUADS);
        glVertex3f(0f,-0.05f,0f);
        glVertex3f(+2f,-0.05f,0f);
        glVertex3f(+2f,+0.05f,0f);
        glVertex3f(0f,+0.05f,0f);
        glVertex3f(+2f,+0.05f,0f);
        glVertex3f(+2f,-0.05f,0f);
        glVertex3f(0f,-0.05f,0f);
        glVertex3f(0f,+0.05f,0f);

        glVertex3f(0f,0f,-0.05f);
        glVertex3f(2f,0f,-0.05f);
        glVertex3f(2f,0f,+0.05f);
        glVertex3f(0f,0f,+0.05f);
        glVertex3f(2f,0f,+0.05f);
        glVertex3f(2f,0f,-0.05f);
        glVertex3f(0f,0f,-0.05f);
        glVertex3f(0f,0f,+0.05f);
        glEnd();
        glColor4d(0.5,0,0,1); // X axis is red.
        glBegin(GL_QUADS);
        glVertex3f(0f,-0.05f,0f);
        glVertex3f(-2f,-0.05f,0f);
        glVertex3f(-2f,+0.05f,0f);
        glVertex3f(0f,+0.05f,0f);
        glVertex3f(-2f,+0.05f,0f);
        glVertex3f(-2f,-0.05f,0f);
        glVertex3f(0f,-0.05f,0f);
        glVertex3f(0f,+0.05f,0f);

        glVertex3f(0f,0f,-0.05f);
        glVertex3f(-2f,0f,-0.05f);
        glVertex3f(-2f,0f,+0.05f);
        glVertex3f(0f,0f,+0.05f);
        glVertex3f(-2f,0f,+0.05f);
        glVertex3f(-2f,0f,-0.05f);
        glVertex3f(0f,0f,-0.05f);
        glVertex3f(0f,0f,+0.05f);
        glEnd();

        glColor4d(0,1,0,1); // Y axis is green.
        glBegin(GL_QUADS);
        glVertex3f(-0.05f,0f,0f);
        glVertex3f(-0.05f,2f,0f);
        glVertex3f(+0.05f,2f,0f);
        glVertex3f(+0.05f,0f,0f);
        glVertex3f(+0.05f,2f,0f);
        glVertex3f(-0.05f,2f,0f);
        glVertex3f(-0.05f,0f,0f);
        glVertex3f(+0.05f,0f,0f);

        glVertex3f(0f,0f,-0.05f);
        glVertex3f(0f,2f,-0.05f);
        glVertex3f(0f,2f,+0.05f);
        glVertex3f(0f,0f,+0.05f);
        glVertex3f(0f,2f,+0.05f);
        glVertex3f(0f,2f,-0.05f);
        glVertex3f(0f,0f,-0.05f);
        glVertex3f(0f,0f,+0.05f);
        glEnd();
        glColor4d(0,0.5,0,1); // Y axis is green.
        glBegin(GL_QUADS);
        glVertex3f(-0.05f,0f,0f);
        glVertex3f(-0.05f,-2f,0f);
        glVertex3f(+0.05f,-2f,0f);
        glVertex3f(+0.05f,0f,0f);
        glVertex3f(+0.05f,-2f,0f);
        glVertex3f(-0.05f,-2f,0f);
        glVertex3f(-0.05f,0f,0f);
        glVertex3f(+0.05f,0f,0f);

        glVertex3f(0f,0f,-0.05f);
        glVertex3f(0f,-2f,-0.05f);
        glVertex3f(0f,-2f,+0.05f);
        glVertex3f(0f,0f,+0.05f);
        glVertex3f(0f,-2f,+0.05f);
        glVertex3f(0f,-2f,-0.05f);
        glVertex3f(0f,0f,-0.05f);
        glVertex3f(0f,0f,+0.05f);
        glEnd();


        glColor4d(0,0,1,1); // Z axis is blue.
        glBegin(GL_QUADS);
        glVertex3f(-0.05f,0f,0f);
        glVertex3f(-0.05f,0f,2f);
        glVertex3f(+0.05f,0f,2f);
        glVertex3f(+0.05f,0f,0f);
        glVertex3f(+0.05f,0f,2f);
        glVertex3f(-0.05f,0f,2f);
        glVertex3f(-0.05f,0f,0f);
        glVertex3f(+0.05f,0f,0f);

        glVertex3f(0f,-0.05f,0f);
        glVertex3f(0f,-0.05f,2f);
        glVertex3f(0f,+0.05f,2f);
        glVertex3f(0f,+0.05f,0f);
        glVertex3f(0f,+0.05f,2f);
        glVertex3f(0f,-0.05f,2f);
        glVertex3f(0f,-0.05f,0f);
        glVertex3f(0f,+0.05f,0f);
        glEnd();
        glColor4d(0,0,0.5,1); // Z axis is blue.
        glBegin(GL_QUADS);
        glVertex3f(-0.05f,0f,0f);
        glVertex3f(-0.05f,0f,-2f);
        glVertex3f(+0.05f,0f,-2f);
        glVertex3f(+0.05f,0f,0f);
        glVertex3f(+0.05f,0f,-2f);
        glVertex3f(-0.05f,0f,-2f);
        glVertex3f(-0.05f,0f,0f);
        glVertex3f(+0.05f,0f,0f);

        glVertex3f(0f,-0.05f,0f);
        glVertex3f(0f,-0.05f,-2f);
        glVertex3f(0f,+0.05f,-2f);
        glVertex3f(0f,+0.05f,0f);
        glVertex3f(0f,-0.05f,0f);
        glVertex3f(0f,+0.05f,0f);
        glVertex3f(0f,+0.05f,-2f);
        glVertex3f(0f,-0.05f,-2f);
        glEnd();

        rd.getCurrentColor().draw();
    }
    
}
