/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.types;

import java.io.Serializable;
import org.modelcc.*;

/**
 * Signed Integer Model.
 * @author elezeta
 * @serial
 */
@Pattern(regExp="(-|\\+)[0-9]+")
public class SignedIntegerModel extends IntegerModel implements IModel,Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    public SignedIntegerModel() {
    }
    
	public SignedIntegerModel(Long val) {
		this.val = val;
	}

	public SignedIntegerModel(Integer val) {
		this.val = val.longValue();
	}
	

    /**
     * Token value.
     */
    @Value
    String textValue;
	
	@Setup
	void run() {
		String stripped;
		if (textValue.startsWith("+"))
			stripped = textValue.substring(1);
		else
			stripped = textValue;
	    val = Long.parseLong(stripped);
	}
	
}
