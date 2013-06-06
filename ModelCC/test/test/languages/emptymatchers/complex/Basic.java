package test.languages.emptymatchers.complex;

import org.modelcc.*;

@Pattern(regExp="")
public class Basic implements IModel {

	@Value
	public String val;
}
