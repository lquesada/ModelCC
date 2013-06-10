/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.*;

/**
 * Decimal literal.
 * @author elezeta
 */
public class DecimalLiteral extends Literal implements IModel {
    
    @Value
    double val;

    public DecimalLiteral() {
        val = 0;
    }
    
    public DecimalLiteral(double val) {
        this.val = val;
    }
    
    @Override
    public int intValue() {
        return (int)val;
    }

    @Override
    public double doubleValue() {
        return val;
    }
    
}
