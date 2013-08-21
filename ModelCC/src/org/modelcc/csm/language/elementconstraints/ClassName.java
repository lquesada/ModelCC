/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.elementconstraints;

import java.io.Serializable;
import org.modelcc.*;

/**
 * Class Name class.
 * @author elezeta
 * @serial
 */
@Pattern(regExp="[a-zA-Z0-9\\_\\-\\.\\$]+")
public class ClassName implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Value
    private String name;
	
    public String getName() {
    	return name;
    }
    
}
