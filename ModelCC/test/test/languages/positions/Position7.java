/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position7 implements IModel {

	public A a;

	public C[] c;
	
	@Position(element="c",position={Position.BEFORE,Position.AFTER,Position.WITHIN})
	public B b;
    
}
