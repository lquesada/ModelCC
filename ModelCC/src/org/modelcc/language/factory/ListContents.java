/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.util.List;

import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.RuleElementPosition;
import org.modelcc.parser.fence.Symbol;

/**
 * List Contents
 * @author elezeta
 * @serial
 */
public class ListContents {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    
    Object[] l;
    
    Symbol extra;
    
    RuleElementPosition extraRuleElement;

    public ListContents(Object[] l,Symbol extra,RuleElementPosition extraRuleElement) {
    	this.l = l;
    	this.extra = extra;
    	this.extraRuleElement = extraRuleElement;
    }

    public ListContents(Object[] l) {
    	this.l = l;
    }
    
    public Object[] getL() {
    	return l;
    }
    
    public Symbol getExtra() {
    	return extra;
    }

    public RuleElementPosition getExtraRuleElement() {
    	return extraRuleElement;
    }
}