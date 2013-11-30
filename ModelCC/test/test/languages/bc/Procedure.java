/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

@Prefix("(P|p)(R|r)(O|o)(C|c)(E|e)(D|d)(I|i)(M|m)(I|i)(E|e)(N|n)(T|t)(O|o)")
public class Procedure implements IModel
{
	private Identifier id;
	
	@Optional
	@Prefix("\\(")
	@Separator(";")	
	@Suffix("\\)")
	private Variable[] parameters;
	
	@Prefix(";")
	private Block block;
	
	
	public Identifier getId ()
	{
		return id;
	}
	
	public Block getBlock ()
	{
		return block;
	}
	
	public Variable[] getParameters ()
	{
		return parameters;
	}
	
	public Variable getParameter (int n)
	{
		return parameters[n];
	}

}
