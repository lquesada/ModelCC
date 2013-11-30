/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.awk;

import org.modelcc.Prefix;
import org.modelcc.Value;

@Prefix("\\$")
public class AWKField extends AWKExpression
{
	@Value
	private int number;  // better than... IntegerLiteral number;

	public int getNumber ()
	{
		return number;
	}
	
	public String toString ()
	{
		return "$"+number;
	}
}
