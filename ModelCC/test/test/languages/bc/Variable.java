/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

public class Variable implements IModel
{
	private Identifier id;
	
	@Prefix(":")
	private Type type;
	
	
	public Identifier getId ()
	{
		return id;
	}
	
	public Type getType ()
	{
		return type;
	}
}
