/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.awk;

import org.modelcc.Composition;
import org.modelcc.CompositionType;
import org.modelcc.IModel;
import org.modelcc.Optional;

@Composition(CompositionType.EAGER)
public class AWKRule implements IModel 
{
	@Optional
	private AWKPattern pattern;
	
	@Optional
	private AWKAction action;

	
	public AWKPattern getPattern ()
	{
		return pattern;
	}
	
	public AWKAction getAction ()
	{
		return action;
	}
}
