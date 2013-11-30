/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.awk;

import org.modelcc.Prefix;

@Prefix("print")
public class AWKPrintStatement extends AWKStatement 
{
	private AWKExpression argument;
	
	public AWKExpression getArgument ()
	{
		return argument;
	}
	
	public String toString ()
	{
		return "print "+argument.toString();
	}

}
