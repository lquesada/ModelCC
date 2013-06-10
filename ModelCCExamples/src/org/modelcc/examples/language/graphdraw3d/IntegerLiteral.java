/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.*;

/**
 * Integer literal.
 * @author elezeta
 */
public class IntegerLiteral extends Literal implements IModel {
    
    @Value
    int val;
    
    public IntegerLiteral() {
        this.val = 0;
    }
     
    public IntegerLiteral(int val) {
        this.val = val;
    }
     
    @Override
    public int intValue() {
        return val;
    }

    @Override
    public double doubleValue() {
        return (double)val;
    }
    
}
