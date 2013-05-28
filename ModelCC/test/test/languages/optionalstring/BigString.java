package test.languages.optionalstring;

import org.modelcc.*;

@Prefix("0")
@FreeOrder
public class BigString implements IModel {

	@Prefix("1")
	@Suffix("2")
	private	OptionalString password;

	public OptionalString getValue() {
		return password;
	}

}
