/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.imperativearithmetic;

import org.modelcc.*;

/**
 * @author elezeta
 */
@Prefix("var")
public class Variable implements IModel {

    @ID
    private Identifier name;
    
    private double value = 0;

    /**
     * @return the value
     */
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
    
}
