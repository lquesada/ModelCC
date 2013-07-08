/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    
    private ElementMember content;
    
    public ContentMember(int position,ElementMember content) {
    	this.position = position;
    	this.content = content;
    }
}
