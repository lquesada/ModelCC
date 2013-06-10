/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.IntegerLiteral;
import org.modelcc.examples.language.graphdraw3d.ObjectName;
import org.modelcc.examples.language.graphdraw3d.Parameter;
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
    Parameter nesting; 
    
    @Override
    public void run(RunData rd) {
        ObjectName name = object.getName();
        if (name != null) {
        	//TODO nesting
            object.draw(rd);
        }
        else 
            object.draw(rd);
    }

	@Override
	public void undo(RunData rd) {
		// TODO Auto-generated method stub
		
	}
}
