package test.languages.emptymatchers.prefix2;

import org.modelcc.*;

@Prefix("c")
public class ContentComplex extends Content implements IModel {

	@Optional
	public Content ct;
}
