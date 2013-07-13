/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class PositionRef1 implements IModel {

	public Obj[] objects;
	
	@Reference
	public Obj[] reflist;    
	
	@Prefix("a")
	@Reference
	@Position(element="reflist",position=Position.WITHIN)
	public Obj ref;
}
