/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.Prefix;

@Prefix("(R|r)(E|e)(P|p)(E|e)(T|t)(I|i)(R|r)")
public class RepeatStatement extends Statement
{
	private Statement statement;
	
	@Prefix("(H|h)(A|a)(S|s)(T|t)(A|a)")
	private Expression condition;
	
	
	
	public Expression getCondition ()
	{
		return condition;
	}
	
	public Statement getStatement ()
	{
		return statement;
	}	
}

