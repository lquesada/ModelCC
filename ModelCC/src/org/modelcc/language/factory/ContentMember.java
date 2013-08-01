/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import org.modelcc.SeparatorPolicy;
import org.modelcc.metamodel.ElementMember;

/**
 * Member node
 * @author elezeta
 * @serial
 */
public class ContentMember implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private int position;
    
    private SeparatorPolicy separatorPolicy;
    
    private ElementMember content;
    
    public ContentMember(int position,SeparatorPolicy separatorPolicy,ElementMember content) {
    	this.position = position;
    	this.separatorPolicy = separatorPolicy;
    	this.content = content;
    }

	public int getPosition() {
		return position;
	}

	public SeparatorPolicy getSeparatorPolicy() {
		return separatorPolicy;
	}

	public ElementMember getContent() {
		return content;
	}

}
