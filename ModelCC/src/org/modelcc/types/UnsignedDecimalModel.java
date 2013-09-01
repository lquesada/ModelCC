/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.types;

import java.io.Serializable;
import org.modelcc.*;

/**
 * Unsigned Decimal Model.
 * @author elezeta
 * @serial
 */
@Pattern(regExp="([0-9]*\\.[0-9]+|[0-9]+\\.[0-9]*)((e|E)(-|\\+)?[0-9]+)?")
public class UnsignedDecimalModel extends DecimalModel implements IModel,Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    public UnsignedDecimalModel() {
    }    

	public UnsignedDecimalModel(Double val) {
		this.val = val;
	}

    /**
     * Token value.
     */
    @Value
    String textValue;
	
	@Setup
	void run() {
	    val = new Double(ScientificNotationParser.parseReal(textValue));
	}
	

}
