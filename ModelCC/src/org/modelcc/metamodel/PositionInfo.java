/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import java.io.Serializable;

import org.modelcc.SeparatorPolicy;

/**
 * Position info.
 * @author elezeta
 * @serial
 */
public class PositionInfo implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

	private ElementMember member;
	
	private int position;
	
	private SeparatorPolicy separatorPolicy;
	
	public PositionInfo(ElementMember member,int position,SeparatorPolicy separatorPolicy) {
		this.member = member;
		this.position = position;
		this.separatorPolicy = separatorPolicy;
	}

	public ElementMember getMember() {
		return member;
	}

	public int getPosition() {
		return position;
	}

	public SeparatorPolicy getSeparatorPolicy() {
		return separatorPolicy;
	}
	  
}
