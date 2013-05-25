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
public class ClausureSpecification extends ConstraintSpecification implements IModel {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Suffix("\\*")
    ConstraintSpecification constraint;
}
