/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.types;

import java.io.Serializable;
import org.modelcc.*;

/**
 * Integer Model.
 * @author elezeta
 * @serial
 */
public class IntegerModel extends NumberModel implements IModel,Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Value.
     */
    protected Long val;

	public IntegerModel() {
	}

	public IntegerModel(Long val) {
		this.val = val;
	}

	public IntegerModel(Integer val) {
		this.val = val.longValue();
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
		return Long.toString(val);
	}


}
