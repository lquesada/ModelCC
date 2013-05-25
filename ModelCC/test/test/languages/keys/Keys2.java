/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys2 implements IModel {
    @ID public ComplexKey key;
    
    @ID public IntData keynum;
    
    public CharData value;
    
    public Keys2() {
        
    }
    
    public Keys2(ComplexKey key,IntData keynum,CharData value) {
        this.key = key;
        this.keynum = keynum;
        this.value = value;
    }
    
}
