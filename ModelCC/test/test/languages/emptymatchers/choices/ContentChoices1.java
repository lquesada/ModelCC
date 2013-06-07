/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.emptymatchers.choices;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="dd")
public class ContentChoices1 extends ContentChoices implements IModel {

	@Value
	public String val;
	
}
