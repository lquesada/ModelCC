/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys1b implements IModel {

    @Prefix("k")
    @Suffix("c")
    @ID public CharData key;
    
    public IntData val;
    
    public Keys1b() {
        
    }

    public Keys1b(CharData key,IntData val) {
        this.key = key;
        this.val = val;
    }
    
}
