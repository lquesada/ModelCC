/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.Sentence;

/**
 * Block Sentence
 * @author elezeta
 */
@Prefix("\\[")
@Suffix("\\]")
public final class GroupSentence extends Sentence implements IModel {
    
    Sentence[] sentences;

    @Override
    public void run(RunData rd,int iter) {
        for (int i = 0;i < sentences.length;i++)
            sentences[i].run(rd,iter);
    }

	@Override
	public void undo(RunData rd,int iter) {
        for (int i = sentences.length-1;i >= 0;i--)
            sentences[i].undo(rd,iter);
	}
}
