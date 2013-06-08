/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.constraints;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Test2 implements IModel {

    @Value int a;
    
    @Constraint boolean test() {
   		return a!=10;
    }

    @Constraint boolean test2() {
   		return a==9;
    }
}
