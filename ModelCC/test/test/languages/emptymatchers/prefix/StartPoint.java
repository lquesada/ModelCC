package test.languages.emptymatchers.prefix;

import org.modelcc.*;

public class StartPoint implements IModel {

	@Prefix("a")
	@Suffix("b")
	public Content content;
}
