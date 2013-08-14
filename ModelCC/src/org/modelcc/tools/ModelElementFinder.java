/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.tools;

import java.io.Serializable;
import java.util.Iterator;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;

/**
 * Model Element Finder
 * @author elezeta
 * @serial
 */
public class ModelElementFinder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    
    private ModelElementFinder() {
    	
    }
    
    public static ModelElement findElement(Model model,String name) throws AmbiguousElementDefinitionException {
		ModelElement me = null;
		for (Iterator<ModelElement> ite = model.getElements().iterator();ite.hasNext();) {
			ModelElement mec = ite.next();
			if (mec.getElementClass().getCanonicalName().endsWith(name) && name.endsWith(mec.getElementClass().getSimpleName())) {
				if (me == null)
					me = mec;
				else
					throw new AmbiguousElementDefinitionException();
			}
		}
		if (me != null)
	    	return me;
		for (Iterator<ModelElement> ite = model.getElements().iterator();ite.hasNext();) {
			ModelElement mec = ite.next();
			if (mec.getElementClass().getName().endsWith(name) && name.endsWith(mec.getElementClass().getSimpleName())) {
				if (me == null)
					me = mec;
				else
					throw new AmbiguousElementDefinitionException();
			}
		}
		if (me != null)
	    	return me;
		for (Iterator<ModelElement> ite = model.getElements().iterator();ite.hasNext();) {
			ModelElement mec = ite.next();
			if (mec.getElementClass().getSimpleName().endsWith(name) && name.endsWith(mec.getElementClass().getSimpleName())) {
				if (me == null)
					me = mec;
				else
					throw new AmbiguousElementDefinitionException();
			}
		}
		if (me != null)
	    	return me;
		return null;
    }

}
