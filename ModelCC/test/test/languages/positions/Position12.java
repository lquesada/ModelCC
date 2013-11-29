/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position12 implements IModel {

	public A a;

	@Multiplicity(minimum=3)
	@Separator(value={"x","y"})
	public C[] c;
	
	@Position(element="c",position=Position.BEFORELAST,separatorPolicy=SeparatorPolicy.REPLACE)
	@Prefix("z")
	@Suffix("w")
	public B b;
    
}
