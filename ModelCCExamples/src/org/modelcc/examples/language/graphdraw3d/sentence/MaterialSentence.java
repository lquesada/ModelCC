/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.IntegerLiteral;
import org.modelcc.examples.language.graphdraw3d.Literal;
import org.modelcc.examples.language.graphdraw3d.RealLiteral;
import org.modelcc.examples.language.graphdraw3d.resources.ColorData;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.Sentence;
import org.modelcc.examples.language.graphdraw3d.resources.Resources;

/**
 * Rotate Sentence
 * @author elezeta
 */
@Prefix("material")
@FreeOrder
public final class MaterialSentence extends Sentence implements IModel {

    @Optional
    Relative rel;

    @Optional
    @Prefix("r")
    Literal r;

    @Optional
    @Prefix("g")
    Literal g;

    @Optional
    @Prefix("b")
    Literal b;

    @Optional
    @Prefix("a")
    Literal a;

    @Optional
    @Prefix("texture")
    IntegerLiteral text;

    @Optional
    Literal all;

    boolean considerAll = false;

    @Setup
    public boolean build() {
        if (r == null && g == null && b == null && a == null && all == null && text == null)
            return false;
        if ((r != null || g != null || b != null || a != null) && all != null)
            return false;
        if (text != null)
            if (text.intValue() >= Resources.maxTextures || text.intValue()<0)
                return false;
        if (all != null)
            considerAll = true;
        else {
            if (r == null)
                r = new RealLiteral(0);
            if (g == null)
                g = new RealLiteral(0);
            if (b == null)
                b = new RealLiteral(0);
            if (a == null)
                a = new RealLiteral(1);
        }

        return true;
    }

    @Override
    public void run(RunData rd) {
        if (rel != null) {
            ColorData cd = rd.getCurrentColor();
            double oldr = cd.getR();
            double oldg = cd.getG();
            double oldb = cd.getB();
            double olda = cd.getA();
            double newr;
            double newg;
            double newb;
            double newa;
            if (considerAll) {
                newr = all.doubleValue();
                newg = all.doubleValue();
                newb = all.doubleValue();
                newa = 0;
            }
            else {
                newr = r.doubleValue();
                newg = g.doubleValue();
                newb = b.doubleValue();
                newa = a.doubleValue();
            }
            ColorData newColor = new ColorData(oldr+newr,oldg+newg,oldb+newb,olda+newa);
            rd.setCurrentColor(newColor);
        }
        else {
            ColorData newColor = null;
            if (considerAll)
                newColor = new ColorData(all.doubleValue(),all.doubleValue(),all.doubleValue(),1.0);
            else
                newColor = new ColorData(r.doubleValue(),g.doubleValue(),b.doubleValue(),a.doubleValue());
            rd.setCurrentColor(newColor);
        }
        if (text != null) {
            if (text.intValue() < Resources.getTextures().length && text.intValue()>=0)
                rd.setCurrentTexture(Resources.getTextures()[text.intValue()]);
        }
    }

    
}
