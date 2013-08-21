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
@Prefix("(?i)associativity")
public class AssociativityElementConstraint extends ElementConstraint implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Prefix("=")
    private AssociativityTypeModel value;

	@Override
	public void apply(Model m, ModelElement me) {
		if (value.getValue().toLowerCase().equals("nonassociative"))
			me.setAssociativity(AssociativityType.NON_ASSOCIATIVE);
		if (value.getValue().toLowerCase().equals("lefttoright"))
			me.setAssociativity(AssociativityType.LEFT_TO_RIGHT);
		if (value.getValue().toLowerCase().equals("righttoleft"))
			me.setAssociativity(AssociativityType.RIGHT_TO_LEFT);
		if (value.getValue().toLowerCase().equals("undefined"))
			me.setAssociativity(AssociativityType.UNDEFINED);
	}

}
