/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;

/**
 * Rule element.
 * @author elezeta
 * @serial
 */
public class RuleElement implements Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * RuleElement type.
     */
    private Object type;

    /**
     * Constructor.
     * @param type the type of the rule element.
     */
    public RuleElement(Object type) {
        this.type = type;
    }

    /**
     * @return the type
     */
    public Object getType() {
        return type;
    }
}
