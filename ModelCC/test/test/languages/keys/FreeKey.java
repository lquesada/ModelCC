/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@FreeOrder
public class FreeKey implements IModel {

    @ID CharData key1;

    @ID IntData key2;
    
    @Prefix("val")
    @Suffix("endval")
    IntData val;
}
