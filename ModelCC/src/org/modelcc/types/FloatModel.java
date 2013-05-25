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
@Pattern(regExp="(-|\\+)?([0-9]*\\.[0-9]+|[0-9]+\\.[0-9]*)((e|E)(-|\\+)?[0-9]+)?")
public class FloatModel extends Number implements IModel,Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Value.
     */
    Float val;
    
    /**
     * Token value.
     */
    @Value
    String textValue;
    
    @Autorun
    void run() {
        val = new Float((float)ScientificNotationParser.parseReal(textValue));
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
    
}
