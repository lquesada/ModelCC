/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.types;

import java.io.Serializable;
import org.modelcc.*;

/**
 * Unsigned Integer Model.
 * @author elezeta
 * @serial
 */
@Pattern(regExp="[0-9]+")
public class UnsignedIntegerModel extends IntegerModel implements IModel,Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    public UnsignedIntegerModel() {
    }
    
	public UnsignedIntegerModel(Long val) {
		this.val = val;
	}
	
    /**
     * Token value.
     */
    @Value
    String textValue;
	
	@Setup
	void run() {
	    val = Long.parseLong(textValue);
	}
	
}
