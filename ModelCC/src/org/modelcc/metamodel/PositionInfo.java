/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import org.modelcc.SeparatorPolicy;

/**
 * Position info.
 * @author elezeta
 */
public class PositionInfo {

	private ModelElement element;
	
	private int position;
	
	private SeparatorPolicy separatorPolicy;
	
	public PositionInfo(ModelElement element,int position,SeparatorPolicy separatorPolicy) {
		this.element = element;
		this.position = position;
		this.separatorPolicy = separatorPolicy;
	}

	public ModelElement getElement() {
		return element;
	}

	public int getPosition() {
		return position;
	}

	public SeparatorPolicy getSeparatorPolicy() {
		return separatorPolicy;
	}
	  
}
