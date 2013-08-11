/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import java.util.List;
import org.modelcc.*;

import org.modelcc.metamodel.Model;

/**
 * Element Name class.
 * @author elezeta
 * @serial
 */
@Pattern(regExp="[a-zA-Z0-9\\_\\-\\.]+")
public class ElementName extends MappingComponent implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Value
    private String name;
	
    @Override
    public void apply(Model model) {
    	
    }
    
}
