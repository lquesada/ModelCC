/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import java.util.List;
import org.modelcc.*;

import org.modelcc.metamodel.Model;

/**
 * Member Mapping Component class.
 * @author elezeta
 * @serial
 */
public abstract class MemberMappingComponent extends MappingComponent implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private MemberID memberID;
	
	private MemberConstraint constraint;
	
    @Override
    public void apply(Model model) {
    	
    }
    

}
