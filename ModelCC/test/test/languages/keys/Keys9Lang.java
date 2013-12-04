/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys9Lang implements IModel {

	@Multiplicity(minimum=1)
	@Optional
    @Reference public Keys1b[] refsbla;

	@Multiplicity(minimum=1)
	@Optional
    @Reference public CompositeData[] refs;
    
    @Prefix("data")
    public CompositeData[] data;
    
}
