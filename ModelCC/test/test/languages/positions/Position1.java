/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position1 implements IModel {

	@Position(element="b",position=Position.AFTER)
	A a;

    B b;
    
}
