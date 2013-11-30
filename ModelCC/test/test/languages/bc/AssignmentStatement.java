/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

public class AssignmentStatement extends Statement 
{
	private Identifier lvalue;
	
	@Prefix(":=")
	private Expression rvalue;
	
	public Identifier getLValue ()
	{
		return lvalue;
	}
	
	public Expression getRValue ()
	{
		return rvalue;
	}

}
