/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys6Lang implements IModel {

    @Optional
    @Prefix("startref") @Suffix("endref") @Reference public Keys1b refs;
    
    @Prefix("data") 
    public Keys1b[] keys1;
    
    
}
