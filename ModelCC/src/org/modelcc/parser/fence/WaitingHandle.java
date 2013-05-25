/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import org.modelcc.language.syntax.Rule;

/**
 * Waiting Handle.
 * @author elezeta
 * @serial
 */
public final class WaitingHandle extends Handle implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Next symbol.
     */
    private ParsedSymbol next;

    /**
     * Default constructor.
     * @param rule the rule
     * @param matched the number of matched elements
     * @param start the start index
     * @param first the first symbol
     * @param next the next matched symbol
     */
    WaitingHandle(Rule rule,int matched,int start,ParsedSymbol first,ParsedSymbol next) {
        super(rule,matched,start,first);
        this.next = next;
    }

    /**
     * @return the next matched symbol
     */
    ParsedSymbol getNext() {
        return next;
    }

    /**
     * Equals method
     * @param obj Object to compare with
     * @return true if equals, false if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WaitingHandle other = (WaitingHandle) obj;
        if (this.rule != other.rule && (this.rule == null || !this.rule.equals(other.rule))) {
            return false;
        }
        if (this.matched != other.matched) {
            return false;
        }
        if (this.start != other.start) {
            return false;
        }
        if (this.next != other.next && (this.next == null || !this.next.equals(other.next))) {
            return false;
        }
        return true;
    }

    /**
     * Unique hashCode
     * @return a hashCode
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.rule != null ? this.rule.hashCode() : 0);
        hash = 83 * hash + this.matched;
        hash = 83 * hash + this.start;
        hash = 83 * hash + (this.next != null ? this.next.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "WaitingHandle Rule "+rule+" matched "+matched+ " first at "+first.getStartIndex()+" next is "+next.getType()+" at "+next.getStartIndex();
    }
    
}
