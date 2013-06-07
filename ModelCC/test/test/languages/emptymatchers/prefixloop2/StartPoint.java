package test.languages.emptymatchers.prefixloop2;

import org.modelcc.*;

public class StartPoint implements IModel {

	@Prefix("a")
	@Suffix("b")
	public Content content;
}
