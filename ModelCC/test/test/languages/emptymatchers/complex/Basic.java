/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.emptymatchers.complex;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="")
public class Basic implements IModel {

	@Value
	public String val;
}
