package test.languages.testlanguage;

import org.modelcc.*;

@Prefix("\\$")
public class DollarPrefix implements IModel {
	@Value
	int number;

	public String toString () {
		return "$"+number;
	}
}
