/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

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
    
    Sentence[] sentences;

    @Override
    public void run(RunData rd,int iter) {
        ColorData cd = rd.getCurrentColor();
        TextureData td = rd.getCurrentTexture();
        for (int i = 0;i < sentences.length;i++)
            sentences[i].run(rd,iter);
        for (int i = sentences.length-1;i >= 0;i--)
            sentences[i].undo(rd,iter);
        rd.setCurrentColor(cd);
        rd.setCurrentTexture(td);
    }

	@Override
	public void undo(RunData rd,int iter) {
	}
        
}
