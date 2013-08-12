/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.memberconstraints;

import java.io.Serializable;
import org.modelcc.*;

import org.modelcc.csm.language.MemberConstraint;
import org.modelcc.metamodel.ElementMember;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;

/**
 * Member Constraint class.
 * @author elezeta
 * @serial
 */
public class PrefixMemberConstraint extends MemberConstraint implements IModel,Serializable {

	//TODO implement 
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

	@Override
	public void apply(Model m, ModelElement me,ElementMember em) {
	}
    
}
