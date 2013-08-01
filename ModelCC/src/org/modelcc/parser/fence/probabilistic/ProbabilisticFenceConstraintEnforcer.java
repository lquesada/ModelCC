/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence.probabilistic;

import java.io.Serializable;
import java.util.Map;

import org.modelcc.language.syntax.Constraints;
import org.modelcc.parser.fence.FenceConstraintEnforcer;
import org.modelcc.parser.fence.ParsedGraph;
import org.modelcc.parser.fence.SyntaxGraph;

/**
 * Fence Constraint Enforcer
 * @author elezeta
 * @serial
 */
public final class ProbabilisticFenceConstraintEnforcer extends FenceConstraintEnforcer implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Perform probabilistic syntactical analysis on a lexical graph.
     * @param constraints the constraints.
     * @param pg the input parsed graph.
     * @return a syntactic analysis graph.
     */
    @Override
	public SyntaxGraph enforce(Constraints constraints, ParsedGraph pg) {
    	return enforce(constraints,pg,null);
    }

    /**
     * Perform probabilistic syntactical analysis on a lexical graph.
     * @param constraints the constraints.
     * @param pg the input parsed graph.
     * @param objectMetadata the object metadata warehouse
     * @return a syntactic analysis graph.
     */
    @Override
	public SyntaxGraph enforce(Constraints constraints, ParsedGraph pg,Map<Object,Map<String,Object>> objectMetadata) {
        ProbabilisticFenceConstraintEnforcerSafe fces = new ProbabilisticFenceConstraintEnforcerSafe();
        return fces.enforce(constraints, pg,objectMetadata);
    }

}
