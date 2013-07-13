/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position5 implements IModel {

	public A a;

	public C[] c;
	
	@Position(element="c",position=Position.WITHIN)
	public B b;
    
}
