/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

@Prefix("(S|s)(I|i)")
public class IfStatement extends Statement
{
	private Expression condition;
	
	@Prefix("(E|e)(N|n)(T|t)(O|o)(N|n)(C|c)(E|e)(S|s)")
	private Statement thenStatement;
	
	@Optional
	@Prefix("(S|s)(I|i)(_| )(N|n)(O|o)")
	private Statement elseStatement;

	
	public Expression getCondition ()
	{
		return condition;
	}
	
	public Statement getThenStatement ()
	{
		return thenStatement;
	}
	
	public Statement getElseStatement ()
	{
		return elseStatement;
	}
}
