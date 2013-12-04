/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys11Lang implements IModel {

	@Multiplicity(minimum=1)
	@Optional
	@Reference public FreeKey[] refs;
    
    @Prefix("data")
    public FreeKey[] data;
    
}
