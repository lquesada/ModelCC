/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import static org.lwjgl.opengl.GL11.*;
import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.Literal;
import org.modelcc.examples.language.graphdraw3d.RealLiteral;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.Sentence;

/**
 * Rotate Sentence
 * @author elezeta
 */
@Prefix("rotate")
@FreeOrder
public final class RotateSentence extends Sentence implements IModel {
    
    @Prefix("angle")
    Literal angle;

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
    public boolean build() {
        if (x == null && y == null && z == null)
            return false;
        if (x == null)
            x = new RealLiteral(0);
        if (y == null)
            y = new RealLiteral(0);
        if (z == null)
            z = new RealLiteral(0);
        if (x.doubleValue() == 0. && y.doubleValue() == 0. && z.doubleValue() == 0.)
            return false;
        return true;
    }

    @Override
    public void run(RunData rd) {
        glRotated(angle.doubleValue(),x.doubleValue(),y.doubleValue(),z.doubleValue());
    }
    
}
