/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

@Prefix("(I|i)(N|n)(I|i)(C|c)(I|i)(O|o)")
@Suffix("(F|f)(I|i)(N|n)")
public class Block extends Statement implements IModel
{
	@Optional
	@Prefix("(V|v)(A|a)(R|r)")
	@Separator(";")
	@Suffix(";")
	private Variable[] variables;
	
	@Optional
	@Separator(";")
	@Suffix(";")
	Procedure[] procedures;
	
	@Optional
	@Separator(";")
	@Suffix(";")
	private Statement[] statements;
	
	
	public Variable[] getVariables ()
	{
		return variables;
	}
	
	public Variable getVariable (int n)
	{
		return variables[n];
	}
	
	public Procedure[] getProcedures ()
	{
		return procedures;
	}
	
	public Procedure getProcedure (int n)
	{
		return procedures[n];
	}
	
	public Statement[] getStatements ()
	{
		return statements;
	}
	
	public Statement getStatement (int n)
	{
		return statements[n];
	}
	
}
