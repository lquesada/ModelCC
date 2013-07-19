/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.inheritlanguage;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Inherit4Content2 extends Inherit4Content1 implements IModel {
	@Position(element="a",position=Position.BEFORE)
	B b;
}
