/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.testlanguage;

import org.modelcc.*;

@Prefix("$")
public class IncorrectDollarPrefix implements IModel {
	@Value
	int number;

	public String toString () {
		return "$"+number;
	}
}
