/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.awk;

import org.modelcc.IModel;

public class AWKProgram implements IModel
{
    AWKRule[] rules;


    public AWKRule[] getRules()
    {
    	return rules;
    }
    
    public AWKRule getRule (int n)
    {
    	return rules[n];
    }
}
