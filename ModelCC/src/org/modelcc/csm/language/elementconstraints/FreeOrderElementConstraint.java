/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.elementconstraints;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelcc.*;

import org.modelcc.csm.CSM;
import org.modelcc.csm.language.ElementConstraint;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.types.BooleanModel;

/**
 * Element Constraint class.
 * @author elezeta
 * @serial
 */
@Prefix("(?i)freeorder")
public class FreeOrderElementConstraint extends ElementConstraint implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Prefix("=")
    private BooleanModel value;

	@Override
	public void apply(Model m, ModelElement me) {
		if (!ComplexModelElement.class.isAssignableFrom(me.getClass())) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"FreeOrder being changed for non complex model element {0}.",new Object[]{me.getElementClass().getCanonicalName()});
		}
		else {
			ComplexModelElement cme = (ComplexModelElement)me;
			cme.setFreeOrder(value.booleanValue());
		}
	}
    
}
