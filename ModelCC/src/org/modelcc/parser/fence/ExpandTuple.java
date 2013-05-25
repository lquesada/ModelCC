/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import java.util.List;
import org.modelcc.language.syntax.Rule;

/**
 * Expand Tuple.
 * @author elezeta
 * @serial
 */
public class ExpandTuple implements Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * The rule.
     */
    private Rule r;
    
    /**
     * The relevant rule.
     */
    private Rule relevant;
    
    /**
     * The list of symbols.
     */
    private List<ParsedSymbol> symbols;

    public ExpandTuple(Rule r,List<ParsedSymbol> symbols) {
        this.r = r;
        this.symbols = symbols;
        this.relevant = null;
    }

    /**
     * @return the rule
     */
    public Rule getRule() {
        return r;
    }

    /**
     * @return the symbols
     */
    public List<ParsedSymbol> getSymbols() {
        return symbols;
    }
    
    /**
     * @return the relevant rule.
     */
    public Rule getRelevant() {
        return relevant;
    }

    /**
     * @param relevant the relevant to set
     */
    public void setRelevant(Rule relevant) {
        this.relevant = relevant;
    }

    
}
