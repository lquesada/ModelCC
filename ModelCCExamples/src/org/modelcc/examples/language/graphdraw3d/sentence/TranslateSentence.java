/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import static org.lwjgl.opengl.GL11.*;
import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.Literal;
import org.modelcc.examples.language.graphdraw3d.DecimalLiteral;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.Sentence;

/**
 * Translate Sentence
 * @author elezeta
 */
@Prefix("translate")
@FreeOrder
public final class TranslateSentence extends Sentence implements IModel {
    
    @Optional
    @Prefix("x")
    Literal x;

    @Optional
    @Prefix("y")
    Literal y;

    @Optional
    @Prefix("z")
    Literal z;

    @Setup
    public void setup() {
    	if (x != null || y != null || z != null) {
            if (x == null)
                x = new DecimalLiteral(0);
            if (y == null)
                y = new DecimalLiteral(0);
            if (z == null)
                z = new DecimalLiteral(0);
    	}
    }
    @Constraint
    public boolean check() {
        if (x == null && y == null && z == null)
            return false;
        return true;
    }

    @Override
    public void run(RunData rd,int iter) {
        glTranslated(x.doubleValue(),y.doubleValue(),z.doubleValue());
    }

	@Override
	public void undo(RunData rd,int iter) {
        glTranslated(-x.doubleValue(),-y.doubleValue(),-z.doubleValue());
	}
}
