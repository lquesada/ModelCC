/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys3Lang implements IModel {

    @Optional
    @Multiplicity(minimum=1)
    @Reference public Keys1[] refs;
    
    @Prefix("data") 
    public Keys1[] keys1;
    
    
}
