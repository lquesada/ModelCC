/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.emptymatchers.basics;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="")
public class ContentBasics extends Content implements IModel {

	@Value
	public String val;
}
