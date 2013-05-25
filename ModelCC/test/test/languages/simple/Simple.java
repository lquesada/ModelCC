/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.simple;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Prefix("a")
public class Simple implements IModel {

    // No genera la optional parece
    @Optional
    Simple ot;

}
