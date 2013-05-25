/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;

/**
 *
 * @author elezeta
 */
@Prefix("define")
public class Definition implements IModel {
    @ID
    ObjectName name;
    
    Sentence sentence;

    public void run(RunData rd) {
        sentence.run(rd);
    }
    
    public ObjectName getName() {
        return name;
    }
    
}
