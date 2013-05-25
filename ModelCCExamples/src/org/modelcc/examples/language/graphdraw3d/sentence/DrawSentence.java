/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.IntegerLiteral;
import org.modelcc.examples.language.graphdraw3d.ObjectName;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.SceneObject;
import org.modelcc.examples.language.graphdraw3d.Sentence;

/**
 * Draw Sentence
 * @author elezeta
 */
@Prefix("draw")
public final class DrawSentence extends Sentence implements IModel {
    
    SceneObject object;

    @Optional
    @Prefix({"max","nesting"})
    IntegerLiteral maxNesting;
    
    @Autorun
    public void build() {
        if (maxNesting == null)
            maxNesting = new IntegerLiteral(10000);
    }
    @Override
    public void run(RunData rd) {
        ObjectName name = object.getName();
        if (name != null) {
            rd.incNesting(object.getName());
            if (rd.getNesting(name)<=maxNesting.intValue())
                object.draw(rd);
            rd.decNesting(object.getName());
        }
        else 
            object.draw(rd);
    }
    
}
