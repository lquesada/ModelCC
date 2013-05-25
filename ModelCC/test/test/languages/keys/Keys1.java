/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys1 implements IModel {

    @ID public CharData key;
    
    public IntData val;
    
    public Keys1() {
        
    }

    public Keys1(CharData key,IntData val) {
        this.key = key;
        this.val = val;
    }
    
}
