/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.types;

import java.io.Serializable;

import org.modelcc.*;

/**
 * Quoted String Model.
 * @author elezeta
 * @serial
 */
@Pattern(regExp="\"(\\\\\"|[^\"])*\"")
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
	
	@Override
	public String toString() {
		return "\""+val.replace("\"","\\\"")+"\"";
	}
	
}