/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position4 implements IModel {

	public A a;

	@Optional
	public C c;
	
	@Position(element="a",position=Position.EXTREME)
	public B b;
    
}
