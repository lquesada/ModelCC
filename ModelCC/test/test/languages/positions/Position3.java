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

	A a;

	@Optional
	C c;
	
	@Position(element="a",position=Position.BEFORE)
    B b;
    
}
