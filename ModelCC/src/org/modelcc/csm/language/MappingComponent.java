/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import org.modelcc.*;

import org.modelcc.metamodel.Model;

/**
 * Mapping Component class.
 * @author elezeta
 * @serial
 */
@Suffix(";")
public abstract class MappingComponent implements IModel,Serializable {

	/**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    public abstract void apply(Model model);
    
}
