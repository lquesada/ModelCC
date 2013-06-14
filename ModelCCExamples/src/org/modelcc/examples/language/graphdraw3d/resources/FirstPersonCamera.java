/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.resources;

import static org.lwjgl.opengl.GL11.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
 
/**
 * First Person Camera
 * @author elezeta
 */
public class FirstPersonCamera {
    private float x;
    private float y;
    private float z;
    private float vx;
    private float vy;
    private float vz;
    private float yaw;
    private float pitch;
    
    public FirstPersonCamera(float x,float y,float z,float yaw,float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vx = 0;
        this.vy = 0;
        this.vz = 0;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    private void acelUp(float distance) {
        vy += distance;
    }

    private void acelDown(float distance) {
        vy -= distance;
    }

    private void acelFront(float distance) {
        vx += distance * (float)Math.sin(Math.toRadians(yaw));
        vz += distance * (float)Math.cos(Math.toRadians(yaw));
    }

    private void acelBack(float distance) {
        vx -= distance * (float)Math.sin(Math.toRadians(yaw));
        vz -= distance * (float)Math.cos(Math.toRadians(yaw));
    }

    private void acelLeft(float distance) {
        vx += distance * (float)Math.sin(Math.toRadians(yaw-90));
        vz += distance * (float)Math.cos(Math.toRadians(yaw-90));
    }

    private void acelRight(float distance) {
        vx += distance * (float)Math.sin(Math.toRadians(yaw+90));
        vz += distance * (float)Math.cos(Math.toRadians(yaw+90));
    }    

    private void yaw(float amount) {
        yaw += amount;
    }

    private void pitch(float amount) {
        pitch += amount;
        if (pitch > 90f)
            pitch = 90f;
        if (pitch < -90f)
            pitch = -90f;
                    
    }

    public void lookThrough() {
        glRotatef(pitch, 1.0f, 0.0f, 0.0f);
        glRotatef(yaw, 0.0f, 1.0f, 0.0f);
        glTranslatef(-x,-y, z);
    }  
    
    public void move(float delta,float mouseSensitivity,float acelFactor,float decelFactor) {
    	if (Mouse.isGrabbed()) {
	        float dx = Mouse.getDX();
	        float dy = Mouse.getDY();
	        yaw(dx*mouseSensitivity);
	        pitch(-dy*mouseSensitivity);
    	}
        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            acelUp(acelFactor*delta);
        }
        
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            acelDown(acelFactor*delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            acelFront(acelFactor*delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            acelBack(acelFactor*delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            acelLeft(acelFactor*delta);
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            acelRight(acelFactor*delta);
        }
        
        x += vx;
        y += vy;
        z += vz;
        
        vx *= decelFactor;
        vy *= decelFactor;
        vz *= decelFactor;
        
    }
           
    
    /**
     * @return the x
     */
    public float getX() {
        return x;
    }

    /**
     * @return the y
     */
    public float getY() {
        return y;
    }

    /**
     * @return the z
     */
    public float getZ() {
        return z;
    }
    
}
