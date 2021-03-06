/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.awk;

import org.modelcc.IModel;
import org.modelcc.Prefix;
import org.modelcc.Suffix;
import org.modelcc.Optional;

@Prefix("\\{")
@Suffix("\\}")
public class AWKAction implements IModel
{
	@Optional
	AWKStatement statement;
	
	public AWKStatement getStatement ()
	{
		return statement;
	}
	
	public String toString () 
	{
		return "{ " + statement + " }";
	}
}
