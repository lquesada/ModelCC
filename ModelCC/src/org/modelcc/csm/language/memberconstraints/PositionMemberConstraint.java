/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.memberconstraints;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelcc.*;

import org.modelcc.csm.CSM;
import org.modelcc.csm.language.ElementConstraint;
import org.modelcc.csm.language.MemberConstraint;
import org.modelcc.csm.language.MemberID;
import org.modelcc.metamodel.ElementMember;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.tools.ElementMemberFinder;
import org.modelcc.tools.NotComplexElementException;
import org.modelcc.types.BooleanModel;

/**
 * Member Constraint class.
 * @author elezeta
 * @serial
 */
@Prefix("(?i)position")
public class PositionMemberConstraint extends MemberConstraint implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Prefix("=")
    @Separator(",")
    List<PositionModel> places;
    
    @Prefix(":")
    MemberID memberId;
    
    @Prefix("\\(")
    @Suffix("\\)")
    @Optional
    SeparatorPolicyModel separatorPolicy;
    
	@Override
	public void apply(Model m, ModelElement me,ElementMember em) {
		Set<Integer> position = new HashSet<Integer>();
		for (int i = 0;i < places.size();i++) {
			if (places.get(i).getValue().toLowerCase().equals("after"))
				position.add(Position.AFTER);
			if (places.get(i).getValue().toLowerCase().equals("before"))
				position.add(Position.BEFORE);
			if (places.get(i).getValue().toLowerCase().equals("beforelast"))
				position.add(Position.BEFORELAST);
			if (places.get(i).getValue().toLowerCase().equals("within"))
				position.add(Position.WITHIN);
		}
		SeparatorPolicy sp = SeparatorPolicy.AFTER;
		if (separatorPolicy != null) {
			if (separatorPolicy.getValue().toLowerCase().equals("after"))
				sp = SeparatorPolicy.AFTER;
			if (separatorPolicy.getValue().toLowerCase().equals("before"))
				sp = SeparatorPolicy.BEFORE;
			if (separatorPolicy.getValue().toLowerCase().equals("extra"))
				sp = SeparatorPolicy.EXTRA;
			if (separatorPolicy.getValue().toLowerCase().equals("replace"))
				sp = SeparatorPolicy.REPLACE;
		}
		int[] posArray = new int[position.size()];
		int i = 0;
		for (Integer in : position) {
			posArray[i] = in;
			i++;
		}
		ElementMember em2 = null;
		try {
			em2 = ElementMemberFinder.findMember(me, memberId.getMemberName().getName());
		} catch (NotComplexElementException e) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Not complex element in CSM mapping: {0}",new Object[]{em.getElementClass().getName()});
			return;
		}

    	if (em2 != null) { 
    		me.setPosition(em,em2, posArray, sp);
        	return;
    	}
		Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Member not found in element {0}: {1}",new Object[]{em.getElementClass().getName(),memberId.getMemberName().getName()});
	}
    
}
