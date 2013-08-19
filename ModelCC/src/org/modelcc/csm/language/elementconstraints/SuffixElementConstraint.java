/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.elementconstraints;

import java.io.Serializable;
import org.modelcc.*;

import org.modelcc.csm.language.ElementConstraint;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.types.BooleanModel;

/**
 * Element Constraint class.
 * @author elezeta
 * @serial
 */
@Prefix("(?i)suffix")
public class SuffixElementConstraint extends ElementConstraint implements IModel,Serializable {

	//TODO implement 
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    //TODO
    BooleanModel a;
    
	@Override
	public void apply(Model m, ModelElement me) {
	}
    
}
