/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position18 implements IModel {

	public A a;

	@Minimum(3)
	@Separator(value={"x","y"})
	public C[] c;
	
	@Position(element="c",position=Position.AROUND,separatorPolicy=SeparatorPolicy.BEFORE)
	@Prefix("z")
	@Suffix("w")
	public B b;
    
}
