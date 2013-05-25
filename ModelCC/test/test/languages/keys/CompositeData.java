/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class CompositeData implements IModel {

    @ID
    @Prefix("\\[")
    @Suffix("\\]")
    @Separator(",")
    public CharData[] key;
    
    @Prefix(":")
    IntData val;
}
