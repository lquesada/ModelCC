package org.modelcc.types;

import java.io.Serializable;

import org.modelcc.*;

@Pattern(regExp="\"([^\\\\\"]+|\\\\([btnfr\"\"\\\\]|[0-3]?[0-7]{1,2}|u[0-9a-fA-F]{4}))*\"|\"([^\\\\\"]+|\\\\([btnfr\"\"\\\\]|[0-3]?[0-7]{1,2}|u[0-9a-fA-F]{4}))*\"")
public class QuotedStringModel extends StringModel implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

	@Value
	String val;
	
	@Setup
	public void fix() {
		val = val.substring(1,val.length()-1);
		val = val.replace("\\\"","\"");
	}
	
	@Override
	public String getValue() {
		return val;
	}
	
}