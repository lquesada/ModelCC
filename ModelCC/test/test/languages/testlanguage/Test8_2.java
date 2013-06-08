/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.testlanguage;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="a|b")
public class Test8_2 implements IModel {
    
    @Value
    String read;
    
    public Test8_2() {
        
    }
    
    @Setup
    public void run() {
    }
    
}
