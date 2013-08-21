/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.elementconstraints;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelcc.*;

import org.modelcc.csm.CSM;
import org.modelcc.csm.language.ElementConstraint;
import org.modelcc.csm.language.ElementID;
import org.modelcc.csm.language.ElementName;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.tools.AmbiguousElementDefinitionException;
import org.modelcc.tools.ModelElementFinder;
import org.modelcc.types.BooleanModel;
import org.modelcc.types.IntegerModel;

/**
 * Element Constraint class.
 * @author elezeta
 * @serial
 */
@Prefix("(?i)precedes")
public class PrecedesElementConstraint extends ElementConstraint implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Prefix("=")
    @Separator(",")
    Set<ElementID> others;
    
	@Override
	public void apply(Model m, ModelElement me) {
		for (Iterator<ElementID> ite = others.iterator();ite.hasNext();) {
			ElementID othername = ite.next();
	    	ModelElement other = null;
			try {
				other = ModelElementFinder.findElement(m, othername.getElementName().getName());
			} catch (AmbiguousElementDefinitionException e) {
				Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Ambiguous element definition in CSM mapping: {0}",new Object[]{othername.getElementName().getName()});
				return;
			}
	    	if (other != null) {
	    		if (me.equals(other))
		    		Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Precedence element is preceding element: {0}",new Object[]{othername.getElementName().getName()});
	    		else
	    			m.addPrecedence(me,other);
	    	}
	    	else {
	    		Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Element not found in CSM mapping: {0}",new Object[]{othername.getElementName().getName()});
	    	}
		}
	}
    
}
