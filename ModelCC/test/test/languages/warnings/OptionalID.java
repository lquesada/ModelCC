/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.warnings;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class OptionalID implements IModel {
    @Optional
    @ID
    OptionalPart a;
}
