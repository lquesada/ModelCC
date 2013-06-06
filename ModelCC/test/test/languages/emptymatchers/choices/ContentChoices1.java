package test.languages.emptymatchers.choices;

import org.modelcc.*;

@Pattern(regExp="dd")
public class ContentChoices1 extends ContentChoices implements IModel {

	@Value
	public String val;
	
}
