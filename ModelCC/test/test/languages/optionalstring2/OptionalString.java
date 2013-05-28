package test.languages.optionalstring2;

import org.modelcc.*;

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
