/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.tools;

import java.io.Serializable;
import java.util.Iterator;

import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.ElementMember;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;

/**
 * Element Member Finder
 * @author elezeta
 * @serial
 */
public class ElementMemberFinder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    
    private ElementMemberFinder() {
    	
    }
    
    public static ElementMember findMember(ModelElement element,String name) throws NotComplexElementException {
		if (!ComplexModelElement.class.isAssignableFrom(element.getClass()))
			throw new NotComplexElementException();
		ComplexModelElement cme = (ComplexModelElement)element;
		for (Iterator<ElementMember> ite = cme.getContents().iterator();ite.hasNext();) {
			ElementMember emc = ite.next();
			if (emc.getField().equals(name)) {
				return emc;
			}
		}
		return null;
    }

}
