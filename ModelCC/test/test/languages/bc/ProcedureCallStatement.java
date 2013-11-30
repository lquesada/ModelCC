/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

public class ProcedureCallStatement extends Statement 
{
	private Identifier id;
	
	@Prefix("\\(")
	@Suffix("\\)")
	@Separator(",")
	@Multiplicity(minimum=0)
	private Expression[] arguments;
	
	
	public Identifier getProcedureId ()
	{
		return id;
	}
	
	public Expression[] getArguments ()
	{
		return arguments;
	}

}
