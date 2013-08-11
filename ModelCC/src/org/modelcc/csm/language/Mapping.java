/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import java.util.List;

import org.modelcc.IModel;
import org.modelcc.metamodel.Model;

/**
 * Mapping class.
 * @author elezeta
 * @serial
 */
public final class Mapping implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private List<MappingComponent> constraints;

    public void apply(Model model) {
    	for (int i = 0;i < constraints.size();i++)
    		constraints.get(i).apply(model);
   }
    
}
