/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.IntegerLiteral;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.Sentence;

/**
 * Block Sentence
 * @author elezeta
 */
@Prefix("repeat")
public final class RepeatSentence extends Sentence implements IModel {
    
    @Suffix("times")
    IntegerLiteral times;

    Sentence sentence;

    @Override
    public void run(RunData rd) {
        for (int i = 0;i < times.intValue();i++) {
            sentence.run(rd);
        }
    }
    
}
