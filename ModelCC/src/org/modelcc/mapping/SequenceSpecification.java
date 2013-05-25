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
@Priority(precedes={AlternationSpecification.class,PrecedenceSpecification.class})
public class SequenceSpecification extends ConstraintSpecification implements IModel {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    ConstraintSpecification[] constraints;
}
