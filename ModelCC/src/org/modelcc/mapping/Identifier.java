/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.mapping;

import org.modelcc.*;

/**
 *
 * @author elezeta
 * @serial
 */
@Pattern(regExp="[a-zA-Z][a-zA-Z0-9_]*")
public class Identifier implements IModel {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Value
    String name;
}
