/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.language.graphdraw3d.objects;

import org.modelcc.*;

/**
 * Object Name
 * @author elezeta
 */
@Pattern(regExp="[A-Za-z][a-zA-Z0-9_]*")
public final class ObjectName implements IModel {
        
    @Value String name;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ObjectName other = (ObjectName) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
    
    @Constraint
    public boolean check() {
    	System.out.println(name);
    	if (name.equals("axis") || name.equals("cube") || name.equals("square"))
    		return false;
    	return true;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
}
