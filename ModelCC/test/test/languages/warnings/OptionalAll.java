/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.warnings;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class OptionalAll implements IModel {
    @Optional
    OptionalPart a;

    @Optional
    OptionalPart b;

    @Optional
    OptionalPart c;
}
