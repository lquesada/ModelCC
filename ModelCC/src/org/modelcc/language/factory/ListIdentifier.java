/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.util.List;

import org.modelcc.SeparatorPolicy;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.metamodel.ModelElement;

/**
 * List Identifier
 * @author elezeta
 * @serial
 */
public class ListIdentifier {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private ModelElement element;
    
    private List<PatternRecognizer> separator;
    
    private boolean zero;

    private boolean ref;

    private ModelElement extra;
   
    private int extraPosition;
    
    private SeparatorPolicy extraSeparatorPolicy;

    public ListIdentifier(ModelElement element,List<PatternRecognizer> separator,boolean ref,boolean zero,ModelElement extra,int extraPosition,SeparatorPolicy extraSeparatorPolicy) {
    	this.element = element;
    	this.separator = separator;
    	this.ref = ref;
    	this.zero = zero;
    	this.extra = extra;
    	this.extraPosition = extraPosition;
    	this.extraSeparatorPolicy = extraSeparatorPolicy;
    }
    
	public ModelElement getElement() {
		return element;
	}

	public List<PatternRecognizer> getSeparator() {
		return separator;
	}

	public boolean isRef() {
		return ref;
	}

	public boolean isZero() {
		return zero;
	}

	public ModelElement getExtra() {
		return extra;
	}

	public int getExtraPosition() {
		return extraPosition;
	}

	public SeparatorPolicy getExtraSeparatorPolicy() {
		return extraSeparatorPolicy;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result + ((extra == null) ? 0 : extra.hashCode());
		result = prime * result + extraPosition;
		result = prime
				* result
				+ ((extraSeparatorPolicy == null) ? 0 : extraSeparatorPolicy
						.hashCode());
		result = prime * result + (ref ? 1231 : 1237);
		result = prime * result
				+ ((separator == null) ? 0 : separator.hashCode());
		result = prime * result + (zero ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ListIdentifier other = (ListIdentifier) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		if (extra == null) {
			if (other.extra != null)
				return false;
		} else if (!extra.equals(other.extra))
			return false;
		if (extraPosition != other.extraPosition)
			return false;
		if (extraSeparatorPolicy != other.extraSeparatorPolicy)
			return false;
		if (ref != other.ref)
			return false;
		if (separator == null) {
			if (other.separator != null)
				return false;
		} else if (!separator.equals(other.separator))
			return false;
		if (zero != other.zero)
			return false;
		return true;
	}

}