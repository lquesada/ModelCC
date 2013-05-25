/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys1Lang implements IModel {

    public Keys1[] keys1;
    
    @Prefix("refs") @Optional
    @Reference public Keys1[] refs;
    
}
