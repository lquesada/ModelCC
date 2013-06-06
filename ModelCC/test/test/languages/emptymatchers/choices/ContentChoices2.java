package test.languages.emptymatchers.choices;

import org.modelcc.*;

@Pattern(regExp="")
public class ContentChoices2 extends ContentChoices implements IModel {

	@Value
	public String val;
	
}
