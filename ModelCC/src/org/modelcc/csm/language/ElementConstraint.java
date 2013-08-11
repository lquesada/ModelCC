/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import java.util.List;
import org.modelcc.*;

import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;

/**
 * Element Constraint class.
 * @author elezeta
 * @serial
 */
public abstract class ElementConstraint implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    
    public abstract void apply(Model m,ModelElement me);
    
}
