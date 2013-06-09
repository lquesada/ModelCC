/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;

/**
 * Scene object.
 * @author elezeta
 */
//TODO @Priority(precedes=ObjectName.class)
public abstract class SceneObject implements IModel {
 
    public abstract void draw(RunData rd);
    
    public ObjectName getName() {
        return null;
    }
    
}
