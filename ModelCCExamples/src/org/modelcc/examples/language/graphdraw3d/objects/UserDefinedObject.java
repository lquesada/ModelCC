/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.objects;

import org.modelcc.examples.language.graphdraw3d.SceneObject;
import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.Definition;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;

/**
 * Axis.
 * @author elezeta
 */
public final class UserDefinedObject extends SceneObject implements IModel {

    @Reference
    Definition object;
    
    @Override
    public void draw(RunData rd,int iter) {
  		object.run(rd,iter);
    }
    
    @Override
    public ObjectName getName() {
        return object.getName();
    }
    
}
