/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.objects.ObjectName;
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

    @Optional
    @Prefix("last")
    Sentence lastSentence;
    
    public void run(RunData rd,int iter) {
    	if (iter>0)
    		sentence.run(rd,iter);
    	else
    		if (lastSentence != null)
    			lastSentence.run(rd,iter);
    }

    public void undo(RunData rd,int iter) {
    	if (iter>0)
    		sentence.undo(rd,iter);
    	else
    		if (lastSentence != null)
    			lastSentence.undo(rd,iter);
    }
    
    public ObjectName getName() {
        return name;
    }
    
}
