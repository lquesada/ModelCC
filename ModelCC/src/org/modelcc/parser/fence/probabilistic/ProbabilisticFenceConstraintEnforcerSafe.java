/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence.probabilistic;

import java.io.Serializable;
import java.util.Map;
import org.modelcc.parser.fence.FenceConstraintEnforcerSafe;
import org.modelcc.parser.fence.Symbol;

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
     * Fills symbol metadata.
     * @param symbol symbol to analyze.
     * @param symbolMap symbol map in which to store metadata.
     */
    @Override
    protected void fillMetadata(Symbol symbol, Map<String, Object> symbolMap) {
    	super.fillMetadata(symbol,symbolMap);
    }

}
