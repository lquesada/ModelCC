/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import java.util.Set;
import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class CompositeDataSet implements IModel {

    @ID
    @Prefix("\\[")
    @Suffix("\\]")
    @Separator(",")
    public Set<CharData> key;
    
    @Prefix(":")
    IntData val;
}
