/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position9 implements IModel {

	A a;

	@Minimum(3)
	C[] c;
	
	@Position(element="c",position=Position.BEFORELAST)
    B b;
    
}
