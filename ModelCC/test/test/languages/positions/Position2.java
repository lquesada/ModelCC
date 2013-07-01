/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position2 implements IModel {

	A a;

	@Position(element="a",position=Position.BEFORE)
    B b;
    
}
