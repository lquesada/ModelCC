/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.optionaltest2;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Test2 implements IModel {
    @Optional 
    Test3 a;
    
    public Test3 getTest() {
        return a;
    }

}
