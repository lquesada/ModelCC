/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class ComplexKey implements IModel {
    public IntData[] data;
    
    public ComplexKey() {
        
    }
    
    public ComplexKey(IntData[] data) {
        this.data = data;
    }
}
