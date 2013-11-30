/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

@Suffix("\\.")
public class Program implements IModel
{
	private Procedure main;
	
	public Procedure getEntryPoint()
	{
		return main;
	}

}
