/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelcc.*;

import org.modelcc.csm.CSM;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.tools.AmbiguousElementDefinitionException;
import org.modelcc.tools.ModelElementFinder;

/**
 * Element Mapping Component class.
 * @author elezeta
 * @serial
 */
public class ElementMappingComponent extends MappingComponent implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

	private ElementID elementId;
	
	private List<ElementConstraint> constraints;

    @Override
    public void apply(Model model) {
    	ModelElement me = null;
		try {
			me = ModelElementFinder.findElement(model, elementId.getElementName().getName());
		} catch (AmbiguousElementDefinitionException e) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Ambiguous element definition in CSM mapping: {0}",new Object[]{elementId.getElementName().getName()});
		}
    	if (me != null) { 
    		for (int i = 0;i < constraints.size();i++)
    			constraints.get(i).apply(model, me);
        	return;
    	}
		Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Element not found in CSM mapping: {0}",new Object[]{elementId.getElementName().getName()});
    }
    
}
