/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys8Lang implements IModel {

    public Keys1b[] keys1;
    
    @Prefix("refs") @Optional
    @Reference public Keys1b[] refs;
    
}
