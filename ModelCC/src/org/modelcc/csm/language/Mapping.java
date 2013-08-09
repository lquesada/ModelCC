/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import java.util.List;

import org.modelcc.metamodel.Model;

/**
 * Mapping class.
 * @author elezeta
 * @serial
 */
public final class Mapping implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    public Model apply(Model original) {
    	//TODO clone model and apply every constraint
		return null;
    }
    
    private List<Constraint> constraints;
}
