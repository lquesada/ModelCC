/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

@Pattern(regExp="[a-zA-Z_Ò—]([a-zA-Z_Ò—]|[0-9])*")
public class Identifier extends Expression implements IModel
{

	@Value
	private String id;
	
	public String toString ()
	{
		return id;
	}
}
