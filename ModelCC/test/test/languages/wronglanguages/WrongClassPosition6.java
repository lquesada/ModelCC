/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.wronglanguages;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class WrongClassPosition6 implements IModel {

	@Position(element="b",position=Position.AFTER)
	OKClass a;
	
	@Position(element="a",position=Position.BEFORE)
	OKClass2 b;
	
}
