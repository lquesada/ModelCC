/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position8 implements IModel {

	A a;

	@Minimum(0)
	C[] c;
	
	@Position(element="c",position=Position.WITHIN)
    B b;
    
}
