/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.*;

/**
 * Sentence.
 * @author elezeta
 */
public abstract class Sentence implements IModel {

    public abstract void run(RunData rd);

    public abstract void undo(RunData rd);
    

}
