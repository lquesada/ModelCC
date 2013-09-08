/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.types;

import java.io.Serializable;
import org.modelcc.*;

/**
 * Signed Decimal Model.
 * @author elezeta
 * @serial
 */
@Pattern(regExp="(-|\\+)([0-9]*\\.[0-9]+|[0-9]+\\.[0-9]*)((e|E)(-|\\+)?[0-9]+)?")
public class SignedDecimalModel extends DecimalModel implements IModel,Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    public SignedDecimalModel() {
    }    

	public SignedDecimalModel(Double val) {
		this.val = val;
	}
	public SignedDecimalModel(Float val) {
		this.val = val.doubleValue();
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
