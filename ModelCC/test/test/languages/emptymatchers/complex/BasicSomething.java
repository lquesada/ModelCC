/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.emptymatchers.complex;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="fdd")
public class BasicSomething implements IModel {

	@Value
	public String val;
}
