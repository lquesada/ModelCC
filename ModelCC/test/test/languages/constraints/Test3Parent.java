/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.constraints;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Test3Parent implements IModel {

	int a=0;
	
	@Constraint
	public boolean test() {
		return a==4;
	}
}
