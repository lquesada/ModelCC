/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelcc.metamodel.ElementMember;

/**
 * Member node
 * @author elezeta
 * @serial
 */
public class MemberNode implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private List<ElementMember> contents;

    private Map<ElementMember,ContentMember> contentMembers;
    
    public MemberNode(ElementMember em) {
    	contents = new ArrayList<ElementMember>();
    	contents.add(em);
    	contentMembers = new HashMap<ElementMember,ContentMember>();
    }

    public MemberNode(MemberNode mn) {
    	contents = new ArrayList<ElementMember>();
    	contents.addAll(mn.getContents());
    	contentMembers = new HashMap<ElementMember,ContentMember>();
    	contentMembers.putAll(mn.getContentMembers());
    }
    
	public List<ElementMember> getContents() {
		return contents;
	}

	public void setContents(List<ElementMember> contents) {
		this.contents = contents;
	}

	public Map<ElementMember,ContentMember> getContentMembers() {
		return contentMembers;
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
