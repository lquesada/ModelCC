/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.modelcc.language.syntax.Rule;
import org.modelcc.language.syntax.RuleElement;

/**
 * Symbol.
 * @author elezeta
 * @serial
 */
public final class Symbol implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * id
     */
    private int id;
    
    /**
     * Contents.
     */
    private List<Symbol> contents;

    /**
     * Rule.
     */
    private Rule rule;

    /**
     * Relevant rule (not E ::= E rule).
     */
    private Rule relevantRule;

    /**
     * List of elements.
     */
    private List<RuleElement> elements;

    /**
     * User data.
     */
    private Object userData;

    /**
     * The parsed symbol.
     */
    private ParsedSymbol ps;
    
    /**
     * Constructor.
     * @param id The symbol id.
     * @param ps The parsed symbol.
     */
    public Symbol(int id,ParsedSymbol ps) {
        this.id = id;
        this.ps = ps;
        this.rule = null;
        this.elements = new ArrayList<RuleElement>();
        this.contents = new ArrayList<Symbol>();
    }

    /**
     * Constructor.
     * @param id the symbol id.
     * @param ps the parsed symbol.
     * @param rule the applied rule applied.
     * @param relevantRule the relevant rule applied.
     * @param elements the used rule elements.
     * @param contents the contents.
     */
    public Symbol(int id,ParsedSymbol ps,Rule rule,Rule relevantRule,List<RuleElement> elements,List<Symbol> contents) {
        this.id = id;
        this.ps = ps;
        this.rule = rule;
        this.relevantRule = relevantRule;
        this.elements = elements;
        this.contents = contents;
    }

    /**
     * Whether if the syntactic symbol is a token or not.
     * @return true if it is a token, false if not.
     */
    public boolean isToken() {
        return ps.isToken();
    }

    /**
     * @return the parsed symbol.
     */
    public ParsedSymbol getParsedSymbol() {
        return ps;
    }

    /**
     * @return the id.
     */
    public int getId() {
        return id;
    }
    
    /**
     * @return the type
     */
    public Object getType() {
        return ps.getType();
    }

    /**
     * @return the rule
     */
    public Rule getRule() {
        return rule;
    }

    /**
     * @return the start index
     */
    public int getStartIndex() {
        return ps.getStartIndex();
    }

    /**
     * @return the end index
     */
    public int getEndIndex() {
        return ps.getEndIndex();
    }

    /**
     * @return the matched rule elements
     */
    public List<RuleElement> getElements() {
        return Collections.unmodifiableList(elements);
    }

    /**
     * @return the contents
     */
    public List<Symbol> getContents() {
        return Collections.unmodifiableList(contents);
    }

    /**
     * @return the relevant rule
     */
    public Rule getRelevantRule() {
        return relevantRule;
    }

    /**
     * @return the userData
     */
    public Object getUserData() {
        return userData;
    }

    /**
     * @param userData the userData to set
     */
    public void setUserData(Object userData) {
        this.userData = userData;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Symbol other = (Symbol) obj;
        return this.id == other.id;
    }

    @Override
    public int hashCode() {
        return this.id;
    }

}
