/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.sentence;

import org.modelcc.*;
import org.modelcc.examples.language.graphdraw3d.IntegerLiteral;
import org.modelcc.examples.language.graphdraw3d.Literal;
import org.modelcc.examples.language.graphdraw3d.DecimalLiteral;
import org.modelcc.examples.language.graphdraw3d.resources.ColorData;
import org.modelcc.examples.language.graphdraw3d.resources.RunData;
import org.modelcc.examples.language.graphdraw3d.Sentence;
import org.modelcc.examples.language.graphdraw3d.resources.Resources;

/**
 * Rotate Sentence
 * @author elezeta
 */
@Prefix("color")
@FreeOrder
public final class ColorSentence extends Sentence implements IModel {

    @Optional
    Relative rel;

    @Optional
    @Prefix("red")
    Literal r;

    @Optional
    @Prefix("green")
    Literal g;

    @Optional
    @Prefix("blue")
    Literal b;

    @Optional
    @Prefix("alpha")
    Literal a;

    @Optional
    Literal all;

    boolean considerAll = false;

    @Setup
    public void setup() {
    	if (r != null || g != null || b != null || a != null || all != null) {
	        if (r == null)
	            r = new DecimalLiteral(0);
	        if (g == null)
	            g = new DecimalLiteral(0);
	        if (b == null)
	            b = new DecimalLiteral(0);
	        if (a == null)
	            a = new DecimalLiteral(1);
	        if (all != null)
	            considerAll = true;
    	}
    }
    @Constraint
    public boolean check() {
        if (r == null && g == null && b == null && a == null)
            return false;
        if ((r != null || g != null || b != null) && all != null)
            return false;
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
    }

	@Override
	public void undo(RunData rd) {
		// TODO Auto-generated method stub
		
	}
    
}
