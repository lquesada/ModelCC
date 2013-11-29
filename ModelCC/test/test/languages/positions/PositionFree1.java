/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@FreeOrder
public class PositionFree1 implements IModel {

	public A a;

	@Multiplicity(minimum=3)
	public C[] c;
	
	@Position(element="c",position=Position.BEFORELAST)
	public B b;
    
}
