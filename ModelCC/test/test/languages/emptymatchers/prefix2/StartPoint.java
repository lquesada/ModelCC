/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.emptymatchers.prefix2;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class StartPoint implements IModel {

	@Prefix("a")
	@Suffix("b")
	public Content content;
}
