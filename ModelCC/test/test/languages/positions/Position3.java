/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position3 implements IModel {

	public A a;

	@Optional
	public C c;
	
	@Position(element="a",position=Position.BEFORE)
	public B b;
    
}
