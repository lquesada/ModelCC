/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


// This language specification by Fernando Berzal

package test.languages.bc;

import org.modelcc.*;

@Prefix("(A|a)(R|r)(R|r)(A|a)(Y|y)")
public class ArrayType extends Type
{
	@Prefix("\\[")
	@Suffix("\\]")
	@Separator(",")
	Range[] ranges;

}
