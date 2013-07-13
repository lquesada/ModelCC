/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Obj implements IModel {

	@Prefix("ID")
	@ID
	public Number number;
	
	public A content;
}
