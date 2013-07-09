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

	A a;

	C[] c;
	
	@Position(element="c",position=Position.AROUND)
    B b;
    
}
