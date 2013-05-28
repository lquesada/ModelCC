/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.optionalstring;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
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
