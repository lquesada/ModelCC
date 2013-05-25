/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import org.modelcc.language.syntax.Rule;


/**
 * Handle
 * @author elezeta
 * @serial
 */
class Handle implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Rule.
     */
    protected Rule rule;

    /**
     * Number of matched elements/next element to match.
     */
    protected int matched;

    /**
     * Start index.
     */
    protected int start;

    /**
     * First matched symbol.
     */
    protected ParsedSymbol first;

    /**
     * Default constructor.
     * @param rule the rule
     * @param matched the number of matched elements
     * @param first the first matched symbol
     */
    Handle(Rule rule,int matched,int start,ParsedSymbol first) {
        this.rule = rule;
        this.matched = matched;
        this.start = start;
        this.first = first;
    }

    /**
     * @return the rule
     */
    Rule getRule() {
        return rule;
    }

    /**
     * @return the matched
     */
    int getMatched() {
        return matched;
    }

    /**
     * @return the first
     */
    int getStart() {
        return start;
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
        final Handle other = (Handle) obj;
        if (this.rule != other.rule && (this.rule == null || !this.rule.equals(other.rule))) {
            return false;
        }
        if (this.matched != other.matched) {
            return false;
        }
        if (this.start != other.start) {
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
        int hash = 5;
        hash = 83 * hash + (this.rule != null ? this.rule.hashCode() : 0);
        hash = 83 * hash + this.matched;
        hash = 83 * hash + this.start;
        return hash;
    }

    /**
     * @return the first symbol
     */
    public ParsedSymbol getFirst() {
        return first;
    }

    @Override
    public String toString() {
        return "Handle Rule "+rule+" matched "+matched+ " first at "+first.getStartIndex();
    }
}
