/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.Prefix;

@Prefix("(M|m)(I|i)(E|e)(N|n)(T|t)(R|r)(A|a)(S|s)")
public class WhileStatement extends Statement
{
	private Expression condition;
	
	@Prefix("(H|h)(A|a)(C|c)(E|e)(R|r)")
	private Statement statement;
	
	
	public Expression getCondition ()
	{
		return condition;
	}
	
	public Statement getStatement ()
	{
		return statement;
	}
}