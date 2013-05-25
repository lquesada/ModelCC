/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.autorun;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Autorun2 implements IModel {
     
    public Autorun5 a;
    
    public int count=0;
    
    @Autorun
    boolean load() {
        count++;
        return true;
    }
}
