/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import org.modelcc.language.syntax.Constraints;

/**
 * Fence Constraint Enforcer
 * @author elezeta
 * @serial
 */
public final class FenceConstraintEnforcer implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Perform syntactical analysis on a lexical graph.
     * @param constraints the constraints.
     * @param pg the input parsed graph.
     * @return a syntactic analysis graph.
     */
    public SyntaxGraph enforce(Constraints constraints, ParsedGraph pg) {
        FenceConstraintEnforcerSafe fces = new FenceConstraintEnforcerSafe();
        return fces.enforce(constraints, pg);
    }

}
