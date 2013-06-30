/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.wronglanguages;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class WrongClassPosition2 implements IModel {

	OKClass[] a;
	
	@Position(element="a",position=Position.WITHIN,separatorPolicy=SeparatorPolicy.REPLACE)
	OKClass2[] b;
	
}
