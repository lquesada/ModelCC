/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.mapping.full;

import org.modelcc.*;
import org.modelcc.mapping.Identifier;

/**
 *
 * @author elezeta
 */
public class Element implements IModel {
    @Separator(".")
    Identifier[] name;
}
