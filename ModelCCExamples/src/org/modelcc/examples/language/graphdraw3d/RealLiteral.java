/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.*;

/**
 * Real literal.
 * @author elezeta
 */
public class RealLiteral extends Literal implements IModel {
    
    @Value
    double val;

    public RealLiteral() {
        val = 0;
    }
    
    public RealLiteral(double val) {
        this.val = val;
    }
    
    
    @Override
    public int intValue() {
        return (int)val;
    }

    @Override
    public long longValue() {
        return (long)val;
    }

    @Override
    public float floatValue() {
        return (float)val;
    }

    @Override
    public double doubleValue() {
        return val;
    }
}
