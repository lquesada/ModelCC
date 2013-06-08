/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.autorun;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Autorun3 implements IModel {
    
    public Autorun6 a;
    
    public int count=0;
    
    @Setup
    Boolean load() {
        count++;
        return true;
    }
}
