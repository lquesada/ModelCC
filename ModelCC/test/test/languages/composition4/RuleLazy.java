/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.composition4;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Composition(CompositionType.LAZY)
public class RuleLazy implements IModel {
    @Optional
	public A a;
    
    @Optional
    public B b;
}
