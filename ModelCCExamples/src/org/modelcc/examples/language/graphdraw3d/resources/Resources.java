/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.resources;

import org.newdawn.slick.opengl.TextureLoader;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author elezeta
 */
@SuppressWarnings("deprecation")
public abstract class Resources {
    
    /* RESOURCES */
	private static TrueTypeFont infoFont;
    private static TextureData[] textures;
    private static org.newdawn.slick.opengl.Texture textureAtlas;

    public final static int maxTextures = 16;

    /**
     * @return the textureAtlas
     */
    public static org.newdawn.slick.opengl.Texture getTextureAtlas() {
        return textureAtlas;
    }

    /**
     * @return the infoFont
     */
	public static TrueTypeFont getInfoFont() {
        return infoFont;
    }
    
    /**
     * @return the textureAtlas
     */
    public static TextureData[] getTextures() {
        return textures;
    }
    
    private Resources() {
        infoFont = null;
        textures = null;
        textureAtlas = null;
    }
    
    public static void load() throws FontFormatException, IOException {
        infoFont = loadFont("org/modelcc/examples/language/graphdraw3d/resources/LucidaTypewriterRegular.ttf",false,10);
        textureAtlas = loadTexture("org/modelcc/examples/language/graphdraw3d/resources/textures.png");
        textures = sliceTexture(textureAtlas,16);
    }
    
	private static TrueTypeFont loadFont(String resourceName,boolean antialias,float size) throws FontFormatException, IOException {
        return new TrueTypeFont(Font.createFont(Font.TRUETYPE_FONT,ResourceLoader.getResourceAsStream(resourceName)).deriveFont(size),antialias);
    }
    
    private static org.newdawn.slick.opengl.Texture loadTexture(String resourceName) throws IOException {
        return TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(resourceName),GL_NEAREST);
    }
    private static TextureData[] sliceTexture(org.newdawn.slick.opengl.Texture texture,int size) throws IOException {
        int w = texture.getImageWidth();
        int h = texture.getImageHeight();
        int cols = w/size;
        int rows = h/size;
        int n = cols*rows;
        TextureData[] texts = new TextureData[n];
        float row;
        float col;
        for (int i = 0;i < n;i++) {
            row = (i/cols);
            col = (i%cols);
            texts[i] = new TextureData((col*16f)/w,(row*16f)/h,((col*16f)+15f)/w,((row*16f)+15f)/h);
        }
        return texts;
    }

}
