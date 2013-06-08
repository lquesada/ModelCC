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
public class Autorun4 implements IModel {
     
    public int count=0;
    
    @Setup
    void load() {
        count++;
    }
}
