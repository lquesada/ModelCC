/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position13 implements IModel {

	A a;

	@Minimum(3)
	@Separator(value={"x","y"})
	C[] c;
	
	@Position(element="c",position=Position.WITHIN,separatorPolicy=SeparatorPolicy.AFTER)
	@Prefix("z")
	@Suffix("w")
    B b;
    
}
