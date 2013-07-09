/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.wronglanguages;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class WrongClassPosition8 implements IModel {

	@Optional
	OKClass[] a;
	
	@Position(element="a",position=Position.AROUND,separatorPolicy=SeparatorPolicy.REPLACE)
	OKClass2 b;
	
}
