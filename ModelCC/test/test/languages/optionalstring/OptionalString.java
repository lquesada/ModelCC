package test.languages.optionalstring;

import org.modelcc.*;

@Pattern(regExp="[a-z]*") 
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
