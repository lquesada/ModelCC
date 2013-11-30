/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

public class Range implements IModel
{
	IntegerLiteral min;
	
	@Prefix("\\.\\.")
	IntegerLiteral max;
	
}
