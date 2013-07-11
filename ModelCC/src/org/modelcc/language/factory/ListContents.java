/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.util.List;

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
    
    Object extra;
    
    public ListContents(Object[] l,Object extra) {
    	this.l = l;
    	this.extra = extra;
    }

    public ListContents(Object[] l) {
    	this.l = l;
    }
    
    public Object[] getL() {
    	return l;
    }
    
    public Object getExtra() {
    	return extra;
    }
        
}