/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.types;

import java.io.Serializable;
import org.modelcc.*;

/**
 * Double Model.
 * @author elezeta
 * @serial
 */
public class DecimalModel extends NumberModel implements IModel,Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Value.
     */
    protected Double val;
    
    public DecimalModel() {
    }
    
	public DecimalModel(Double val) {
		this.val = val;
	}

	public DecimalModel(Float val) {
		this.val = val.doubleValue();
	}
	
    @Override
    public int intValue() {
        return val.intValue();
    }

    @Override
    public long longValue() {
        return val.longValue();
    }

    @Override
    public float floatValue() {
        return val.floatValue();
    }

    @Override
    public double doubleValue() {
        return val.doubleValue();
    }

    @Override
    public byte byteValue() {
        return val.byteValue();
    }
    
	@Override
	public String toString() {
		return Double.toString(val);
	}

}
