/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.wronglanguages;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class WrongClassPositionClash2 implements IModel {

	OKClass[] a;
	
	@Position(element="a",position=Position.BEFORE,separatorPolicy=SeparatorPolicy.REPLACE)
	OKClass c;
	
	@Position(element="a",position={Position.BEFORE,Position.AFTER},separatorPolicy=SeparatorPolicy.REPLACE)
	OKClass2 b;
	
}
