/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.resources;

import java.awt.FontFormatException;
import java.io.IOException;
import org.lwjgl.opengl.PixelFormat;
import org.lwjgl.input.Mouse;
import org.lwjgl.input.Keyboard;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelcc.*;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.modelcc.examples.language.graphdraw3d.Scene;
import org.newdawn.slick.Color;
import org.modelcc.examples.language.graphdraw3d.resources.Resources;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.Sys.*;

/**
 * Display wrapper.
 * @author elezeta
 */
public final class DisplayWrapper implements IModel {
    
    /* COUNTERS */
    private long lastFrame;
    private float delta;
    private float fps;
    private float fpsSoft;
    private float deltaCarry;
    
    /* CONFIG */
    private int maxFPS;
    private int minWidth;
    private int minHeight;
    private int maxWidth;
    private int maxHeight;
    private int width;
    private int height;
    private float mouseSensitivity;
    private float acelFactor;
    private float decelFactor;
    
    /* ENTITIES */
    private FirstPersonCamera fpc;
    private Scene scene;

    private boolean running;
    
    /* 3D */
    private boolean simulate3D;
    private boolean simulate3Dleft;
    private float simulate3Dcurrent;
    private float simulate3Dcycle;
    private float simulate3Dstrength;
    private float simulate3Dangle;
    
    public DisplayWrapper(Scene scene) {
        this.scene = scene;
    }
    
    public void run() {
    	simulate3D = false;
    	simulate3Dleft = true;
    	simulate3Dcurrent = 0;
    	simulate3Dcycle = 0.1f;
    	simulate3Dstrength = 0.3f;
    	simulate3Dangle = 5f;
        maxFPS = 80;
        width = 1000;
        height = 800;
        minWidth = 320;
        minHeight = 200;
        maxWidth = 32000;
        maxHeight = 20000;
        setRunning(true);
        mouseSensitivity = 0.2f;
        acelFactor = 3.3f;
        decelFactor = 0.6f;
        fpc = new FirstPersonCamera(-3,4,-4,25,18);
        
        fpsSoft = maxFPS;
        try {
            initDisplay();
        } catch (Exception ex) {
            Logger.getLogger(Scene.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Mouse.setGrabbed(false);
        
	while (!Display.isCloseRequested() && !Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            calculateDelta();
            calculateFPS();
            checkDisplay();
            Display.setTitle("Graph Draw 3D - "+((int)fpsSoft)+" fps");
            manageInput();
            fpc.move(delta,mouseSensitivity,acelFactor,decelFactor);
            glClear(GL_DEPTH_BUFFER_BIT|GL_COLOR_BUFFER_BIT);
        	checkSimulate3D();
            render();
            Display.update();
            Display.sync(maxFPS);
        }
		stop();
    }
    
    private void checkSimulate3D() {
    	simulate3Dcurrent += delta;
    	if (simulate3Dcurrent>simulate3Dcycle) {
    		simulate3Dleft = !simulate3Dleft;
    		simulate3Dcurrent %= simulate3Dcycle;
    	}
		
	}

	private void checkDisplay() {
		if (Display.wasResized()) {
			width = Display.getWidth();
			height = Display.getHeight();
			boolean resize = false;
			if (width < minWidth) {
				width = minWidth;
				resize = true;
			}
			if (width > maxWidth) {
				width = maxWidth;
				resize = true;
			}
			if (height < minHeight) {
				height = minHeight;
				resize = true;
			}
			if (height > maxHeight) {
				height = maxHeight;
				resize = true;
			}
			if (resize) {
				try {
					setSize();
				} catch (LWJGLException e) {
					e.printStackTrace();
					System.exit(-1);
				}
			}
		}
		if (!Display.isActive()) {
			Mouse.setGrabbed(false);
		}

	}
    
	private void setSize() throws LWJGLException {
		if (height>Display.getDesktopDisplayMode().getHeight())
			height = Display.getDesktopDisplayMode().getHeight();
		if (width>Display.getDesktopDisplayMode().getWidth())
			width = Display.getDesktopDisplayMode().getWidth();
		Display.setDisplayMode(new DisplayMode(width, height));
	}

	private void manageInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) {
		        if (Keyboard.getEventKey() ==Keyboard.KEY_Q) {
		        	if (Mouse.isGrabbed())
		        		Mouse.setGrabbed(false);
		        	else
		        		Mouse.setGrabbed(true);
		        }
		        if (Keyboard.getEventKey() ==Keyboard.KEY_3) {
		        	simulate3D = !simulate3D;
		        }
			}
		}
		while (Mouse.next()) {
			if (Mouse.getEventButtonState()) {
				if (Mouse.isButtonDown(0) || Mouse.isButtonDown(1) || Mouse.isButtonDown(2)) {
					if (Mouse.isGrabbed())
						Mouse.setGrabbed(false);
					else
						Mouse.setGrabbed(true);
				}
			}
		}

	}

	public void stop() {
        Mouse.setGrabbed(false);
        Mouse.destroy();
        Keyboard.destroy();
        Display.destroy();
        setRunning(false);
    }
    
    private void initDisplay() throws LWJGLException, FontFormatException, IOException {
        Display.setTitle("Graph Draw 3D - "+((int)fpsSoft)+" fps");
		Display.setResizable(true);
        setSize();
        try {
            Display.create(new PixelFormat(0,8,0,45));
        } catch (LWJGLException e) {
            Display.create();
        }
        Display.makeCurrent();
        Keyboard.create();
        Mouse.create();
        //Display.setDisplayConfiguration(1,1,1);
        Resources.load();
        Display.setVSyncEnabled(false);

        glShadeModel(GL_FLAT);
        glEnable(GL_CULL_FACE);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_ALPHA_TEST);
        glAlphaFunc(GL_GREATER,0f);
        glDisable(GL_LIGHTING);                    
        glEnable(GL_TEXTURE_2D);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_NEAREST);
        glEnable(GL_BLEND);
        

        //glClearColor(0.53f, 0.70f, 0.93f, 0.0f);                
        glClearColor(1f, 1f, 1f, 0f);                
        //glClearColor(0f, 0f, 0f, 0f);                
        glClearDepth(1d);                                       
    }
    
    public void calculateFPS() {
        if (delta == 0f)
            fps = maxFPS;
        else
            fps = 1f/delta;
        deltaCarry += delta;
        if (deltaCarry >= 0.5) {
            deltaCarry %= 0.5;
            fpsSoft = fps;
        }        
    }
    
    public void calculateDelta() {
        long time = getTime();
        delta = (time - lastFrame)/1000f;
        lastFrame = time;
    }
    
    private void render() {

		glViewport(0, 0, width, height);

		float wview = (0.11f)/2f;
        float hview = (0.11f*((float)height)/((float)width))/2f;
        float viewmin =  0.05f;

		// WORLD
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glFrustum(-wview,wview,-hview,hview,viewmin, 2000f);

        glMatrixMode(GL_MODELVIEW);
        glClear(GL_DEPTH_BUFFER_BIT);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glLoadIdentity();

    	fpc.lookThrough();
        
        glTranslatef(0f,0f,-4f);

        Resources.getTextureAtlas().bind();
        //TODO 3D
        if (simulate3D) {
        	if (simulate3Dleft) {
//                glTranslatef(-1f,0f,0f);
        	}
        	else {
//                glTranslatef(1f,0f,0f);
        	}
        }
        scene.draw();

        // HUD
        
        glMatrixMode(GL_PROJECTION);
        glPushMatrix();
        glLoadIdentity();
        glOrtho(0,width,height,0, 1, -1);
        glClear(GL_DEPTH_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glColor3f(1f,1f,1f);


        Resources.getInfoFont().drawString(3,3,"Press ESC to exit. Press W/A/S/D/space bar/left shift to move. Press Q or click to grab/release mouse. Move mouse to look around.  Press 3 for 3D effect.", Color.black);
        
        glMatrixMode(GL_PROJECTION);
        glPopMatrix();        
    }

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
    
	public Scene getScene() {
		return scene;
	}
}
