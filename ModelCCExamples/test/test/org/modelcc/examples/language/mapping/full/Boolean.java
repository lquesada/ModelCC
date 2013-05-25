/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.full;

import org.modelcc.*;
import test.org.modelcc.examples.language.mapping.full.Literal;

/**
 *
 * @author elezeta
 */
class Boolean extends Literal implements IModel {
    @Value
    boolean value;
}
