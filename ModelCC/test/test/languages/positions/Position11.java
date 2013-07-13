/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position11 implements IModel {

	public A a;

	@Minimum(3)
	@Separator(value={"x","y"})
	public C[] c;
	
	@Position(element="c",position=Position.BEFORELAST,separatorPolicy=SeparatorPolicy.EXTRA)
	@Prefix("z")
	@Suffix("w")
	public B b;
    
}
