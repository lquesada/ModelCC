/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Keys10Lang implements IModel {

    @Reference public CompositeDataSet[] refs;
    
    @Prefix("data")
    public CompositeDataSet[] data;
    
}
