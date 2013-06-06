package test.languages.emptymatchers.basics;

import org.modelcc.*;

@Pattern(regExp="")
public class ContentBasics extends Content implements IModel {

	@Value
	public String val;
}
