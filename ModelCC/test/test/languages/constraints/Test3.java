/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.constraints;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Test3 extends Test3Parent implements IModel {

	@Value
	int b;
	@Setup
	public void setup() {
		a = 4;
	}
}
