/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;

/**
 * Rule element position.
 * @author elezeta
 * @serial
 */
public final class RuleElementPosition extends RuleElement implements Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Id of the element. Can be user defined to identify the position of the matching tokens.
     */
    private Object positionId;

    /**
     * Constructor.
     * @param type the type of the rule element.
     * @param positionId the position id.
     */
    public RuleElementPosition(Object type,Object positionId) {
        super(type);
        this.positionId = positionId;
    }

    /**
     * @return the id
     */
    public Object getPositionId() {
        return positionId;
    }

    /**
     * @param positionId the id to set
     */
    public void setPositionId(Object positionId) {
        this.positionId = positionId;
    }

}
