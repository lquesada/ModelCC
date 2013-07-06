/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.List;

import org.modelcc.metamodel.ElementMember;

/**
 * Member node
 * @author elezeta
 * @serial
 */
public abstract class MemberNode implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private List<ElementMember> contents;

	public List<ElementMember> getContents() {
		return contents;
	}

	public void setContents(List<ElementMember> contents) {
		this.contents = contents;
	}

	public ElementMember getFront() {
		if (contents.size()==0)
			return null;
		return contents.get(0);
	}

	public ElementMember getBack() {
		if (contents.size()==0)
			return null;
		return contents.get(contents.size()-1);
	}

}
