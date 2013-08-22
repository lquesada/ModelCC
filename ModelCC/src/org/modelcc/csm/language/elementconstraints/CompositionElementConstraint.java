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
@Prefix("(?i)composition")
public class CompositionElementConstraint extends ElementConstraint implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Prefix("=")
    private CompositionTypeModel value;

	@Override
	public void apply(Model m, ModelElement me) {
		if (!ComplexModelElement.class.isAssignableFrom(me.getClass())) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Composition type being changed for non complex model element {0}.",new Object[]{me.getElementClass().getCanonicalName()});
		}
		else {
			ComplexModelElement cme = (ComplexModelElement)me;
			if (value.getValue().toLowerCase().equals("undefined"))
				cme.setComposition(CompositionType.UNDEFINED);
			if (value.getValue().toLowerCase().equals("eager"))
				cme.setComposition(CompositionType.EAGER);
			if (value.getValue().toLowerCase().equals("lazy"))
				cme.setComposition(CompositionType.LAZY);
			if (value.getValue().toLowerCase().equals("explicit"))
				cme.setComposition(CompositionType.EXPLICIT);
		}
	}
}
