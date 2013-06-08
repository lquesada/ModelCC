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
public class Test8 implements IModel {
    
    @Value
    String read;
    
    public Test8() {
        
    }
    
    @Constraint
    public boolean run() {
        if (read.equals("a"))    
            return true;
        else
            return false;
    }
    
}
