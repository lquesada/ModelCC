/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.Prefix;

@Prefix("(E|e)(N|n)(T|t)(R|r)(A|a)(D|d)(A|a)")
public class InputStatement extends Statement 
{
	Identifier variable;
	
	public Identifier getVariable ()
	{
		return variable;
	}
	
	
	public String toString ()
	{
		return "entrada "+variable.toString();
	}
}
