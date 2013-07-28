/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import org.modelcc.language.syntax.AssociativityConstraint;
import org.modelcc.language.syntax.Constraints;
import org.modelcc.language.syntax.Rule;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.RuleElementPosition;

/**
 * Fence Constraint Enforcer
 * @author elezeta
 * @serial
 */
public final class ProbabilisticFenceConstraintEnforcerSafe extends FenceConstraintEnforcerSafe implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Builds a symbol, filling its data, and validates it.
     * @param s symbol to be built.
     * @return true if the symbol is valid, false if not
     */
    @Override
    protected boolean build(Rule r,Symbol s) {
    	//TODO probabilities
        return r.getBuilder().build(s,data);
    }

}
