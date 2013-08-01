/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.optionalstring;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="[a-z]*") 
public class OptionalString implements IModel {

	@Value
	String value;

	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return "OptionalString("+value+")";
	}

}
