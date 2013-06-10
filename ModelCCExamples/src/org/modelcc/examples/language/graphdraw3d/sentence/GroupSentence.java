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
    
    @Minimum(0)
    Sentence[] sentences;

    @Override
    public void run(RunData rd) {
        for (int i = 0;i < sentences.length;i++)
            sentences[i].run(rd);
    }

	@Override
	public void undo(RunData rd) {
		// TODO Auto-generated method stub
		
	}
}
