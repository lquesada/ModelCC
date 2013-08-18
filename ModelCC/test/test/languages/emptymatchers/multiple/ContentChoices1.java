/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.emptymatchers.multiple;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="d?")
public class ContentChoices1 extends ContentChoices implements IModel {

	@Value
	public String val;
	
}
