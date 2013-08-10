/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm;

import java.io.Reader;
import java.io.Serializable;

import org.modelcc.metamodel.Model;

/**
 * Simple Mapping Reader class.
 * @author elezeta
 * @serial
 */
public final class CSM implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Apply CSM to a model
     * @param model model
     * @param input CSM text
     * @return constrained model.
     */
    public Model apply(Model model,String input) {
    	Model clone = model.clone();
    	//TODO
		return null;
	}

    /**
     * Apply CSM to a model
     * @param model model
     * @param input CSM text
     * @return constrained model.
     */
    public Model apply(Model model,Reader input) {
    	//TODO
		return null;
	}
    
}
