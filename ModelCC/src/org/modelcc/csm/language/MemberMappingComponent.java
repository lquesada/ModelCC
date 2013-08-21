/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelcc.*;

import org.modelcc.csm.CSM;
import org.modelcc.metamodel.ElementMember;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.tools.AmbiguousElementDefinitionException;
import org.modelcc.tools.ElementMemberFinder;
import org.modelcc.tools.ModelElementFinder;
import org.modelcc.tools.NotComplexElementException;

/**
 * Member Mapping Component class.
 * @author elezeta
 * @serial
 */
public class MemberMappingComponent extends MappingComponent implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

	private ElementID elementId;

	@Prefix("\\[")
	@Suffix("\\]")
	private MemberID memberId;
	
	private List<MemberConstraint> constraints;

    @Override
    public void apply(Model model) {
    	ModelElement me = null;
		try {
			me = ModelElementFinder.findElement(model, elementId.getElementName().getName());
		} catch (AmbiguousElementDefinitionException e) {
			Logger.getLogger(MemberMappingComponent.class.getName()).log(Level.SEVERE,"Ambiguous element definition in CSM mapping: {0}",new Object[]{elementId.getElementName().getName()});
			return;
		}
		if (me == null)
			return;
		ElementMember em = null;
		try {
			em = ElementMemberFinder.findMember(me, memberId.getMemberName().getName());
		} catch (NotComplexElementException e) {
			Logger.getLogger(MemberMappingComponent.class.getName()).log(Level.SEVERE,"Not complex element in CSM mapping: {0}",new Object[]{elementId.getElementName().getName()});
			return;
		}

    	if (em != null) { 
    		for (int i = 0;i < constraints.size();i++)
    			constraints.get(i).apply(model, me,em);
        	return;
    	}
		Logger.getLogger(MemberMappingComponent.class.getName()).log(Level.SEVERE,"Member not found in element {0}: {1}",new Object[]{elementId.getElementName().getName(),memberId.getMemberName().getName()});
    }
    

}
