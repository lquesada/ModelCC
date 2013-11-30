/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

@Prefix("'")
@Suffix("'")
@Pattern(regExp="([^']|'')*")
public class StringLiteral extends Expression implements IModel
{
	@Value
	String value;
	
	public String toString ()
	{
		return "'"+value+"'";
	}
}
