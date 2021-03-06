/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d;

import org.modelcc.*;

/**
 * Literal.
 * @author elezeta
 */
public abstract class Literal extends Parameter implements IModel {

	public abstract int intValue();

	public abstract double doubleValue();

}
