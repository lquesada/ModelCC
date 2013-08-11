/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import java.util.List;
import org.modelcc.*;

import org.modelcc.metamodel.Model;

/**
 * Element Mapping Component class.
 * @author elezeta
 * @serial
 */
public abstract class ElementMappingComponent extends MappingComponent implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

	private ElementID elementName;
	
	private ElementConstraint constraint;
	
    @Override
    public void apply(Model model) {
    	
    }
    
}
