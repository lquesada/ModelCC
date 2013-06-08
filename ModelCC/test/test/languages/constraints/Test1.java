/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.constraints;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Test1 implements IModel {

    @Value int a;
    
    @Constraint boolean test() {
   		return a!=10;
    }
}
