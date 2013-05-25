/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import static org.lwjgl.opengl.GL11.*;
import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.Sentence;
import org.modelcc.examples.language.graphdraw3d.resources.ColorData;
import org.modelcc.examples.language.graphdraw3d.resources.TextureData;

/**
 * Block Sentence
 * @author elezeta
 */
@Prefix("\\{")
@Suffix("\\}")
public final class BlockSentence extends Sentence implements IModel {
    
    @Minimum(0)
    Sentence[] sentences;

    @Override
    public void run(RunData rd) {
        ColorData cd = rd.getCurrentColor();
        TextureData td = rd.getCurrentTexture();
        glPushMatrix();
        for (int i = 0;i < sentences.length;i++)
            sentences[i].run(rd);
        glPopMatrix();
        rd.setCurrentColor(cd);
        rd.setCurrentTexture(td);
    }
        
}
