/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.full;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="[a-zA-Z][a-zA-Z0-9_]*")
public class Identifier implements IModel {
    @Value
    String name;
}
