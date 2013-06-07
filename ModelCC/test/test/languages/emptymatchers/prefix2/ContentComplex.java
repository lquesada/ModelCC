/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.emptymatchers.prefix2;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Prefix("c")
public class ContentComplex extends Content implements IModel {

	@Optional
	public Content ct;
}
