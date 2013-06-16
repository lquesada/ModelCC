/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.objects.ObjectName;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;

/**
 * Scene object.
 * @author elezeta
 */
public abstract class SceneObject implements IModel {
 
    public abstract void draw(RunData rd,int iter);
    
    public ObjectName getName() {
        return null;
    }
    
}
