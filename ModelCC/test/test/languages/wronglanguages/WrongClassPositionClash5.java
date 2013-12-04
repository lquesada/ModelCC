/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.wronglanguages;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class WrongClassPositionClash5 implements IModel {

	@Multiplicity(minimum=1)
	OKClass[] a;
	
	@Position(element="a",position=Position.WITHIN,separatorPolicy=SeparatorPolicy.REPLACE)
	OKClass c;
	
	@Position(element="a",position=Position.BEFORELAST,separatorPolicy=SeparatorPolicy.REPLACE)
	OKClass2 b;
	
}
