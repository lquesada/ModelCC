/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Position6 implements IModel {

	public A a;

	@Multiplicity(minimum=1)
	public C[] c;
	
	@Position(element="c",position=Position.BEFORELAST)
	public B b;
    
}
