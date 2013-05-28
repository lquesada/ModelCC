/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.optionalstring2;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="") 
public class OptionalString implements IModel {

	@Value
	String value;

	public String getValue() {
		return value;
	}
	
	public String toString() {
		return "OptionalString("+value+")";
	}

}
