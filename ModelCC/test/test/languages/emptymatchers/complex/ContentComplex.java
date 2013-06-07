/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.emptymatchers.complex;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class ContentComplex extends Content implements IModel {

	public Basic empty;
	
	@Optional
	public BasicSomething something;
}
