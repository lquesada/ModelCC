/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.autorun;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="a")
public class Autorun5 implements IModel {
     
    public int count=0;
    
    @Setup
    void load() {
        count++;
    }
    @Constraint boolean checkConstraint() {
        return true;
    }
}
