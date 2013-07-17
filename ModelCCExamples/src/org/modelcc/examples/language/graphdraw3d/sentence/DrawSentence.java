/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.IntegerLiteral;
import org.modelcc.examples.language.graphdraw3d.Literal;
import org.modelcc.examples.language.graphdraw3d.Next;
import org.modelcc.examples.language.graphdraw3d.Parameter;
import org.modelcc.examples.language.graphdraw3d.objects.ObjectName;
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
    Parameter iters; 
    
    @Override
    public void run(RunData rd,int iter) {
        ObjectName name = object.getName();
        int nextIter = 1;
        if (iters != null) {
        	if (Next.class.isAssignableFrom(iters.getClass())) {
        		nextIter = iter-1;
        	}
        	else if (Literal.class.isAssignableFrom(iters.getClass())) {
        		nextIter = ((Literal)iters).intValue();
        	}
        }
        object.draw(rd,nextIter);
    }

	@Override
	public void undo(RunData rd,int iter) {
        ObjectName name = object.getName();
        int nextIter = 1;
        if (iters != null) {
        	if (Next.class.isAssignableFrom(iters.getClass())) {
        		nextIter = iter-1;
        	}
        	else if (Literal.class.isAssignableFrom(iters.getClass())) {
        		nextIter = ((Literal)iters).intValue();
        	}
        }
        object.undo(rd,nextIter);
	}
}
