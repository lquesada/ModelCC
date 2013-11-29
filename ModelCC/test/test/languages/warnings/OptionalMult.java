/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.warnings;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class OptionalMult implements IModel {
    @Optional
    @Multiplicity(minimum=0)
    OptionalPart[] a;
}
