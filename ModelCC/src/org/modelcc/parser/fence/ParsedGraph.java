/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.modelcc.language.syntax.Grammar;
import org.modelcc.language.syntax.Rule;

/**
 * Syntactic Graph
 * @author elezeta
 * @serial
 */
public final class ParsedGraph implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Set of symbols used in this graph.
     */
    private Set<ParsedSymbol> symbols;

    /**
     * Set of start symbols of this graph.
     */
    private Set<ParsedSymbol> start;

    /**
     * List of preceding symbols.
     */
    private Map<ParsedSymbol,Set<ParsedSymbol>> preceding;

    /**
     * List of following symbols.
     */
    private Map<ParsedSymbol,Set<ParsedSymbol>> following;

    /**
     * Start positions
     */
    Map<Integer,Set<ParsedSymbol>> startPositions;

    /**
     * End positions
     */
    Map<Integer,Set<ParsedSymbol>> endPositions;

    /**
     * Element rules.
     */
    private Map<Object,Set<Rule>> elementRules;

    /**
     * Grammar.
     */
    private Grammar grammar;

    /**
     * Default constructor.
     * @param symbols the symbol set
     * @param start the start symbol
     * @param preceding the preceding map
     * @param following the following map
     * @param grammar the grammar
     */
    public ParsedGraph(Set<ParsedSymbol> symbols,Set<ParsedSymbol> start,Map<ParsedSymbol,Set<ParsedSymbol>> preceding,Map<ParsedSymbol,Set<ParsedSymbol>> following,Grammar grammar) {
        this.symbols = symbols;
        this.start = start;
        this.preceding = preceding;
        this.following = following;
        this.startPositions = new HashMap<Integer,Set<ParsedSymbol>>();
        this.endPositions = new HashMap<Integer,Set<ParsedSymbol>>();
        this.grammar = grammar;

        Iterator<ParsedSymbol> ite;
        Set<ParsedSymbol> aux;
        ParsedSymbol p;
        int i;
        for (ite = symbols.iterator();ite.hasNext();) {
            p = ite.next();
            i = p.getStartIndex();
            aux = startPositions.get(i);
            if (aux == null) {
                aux = new HashSet<ParsedSymbol>();
                startPositions.put(i,aux);
            }
            aux.add(p);
            i = p.getEndIndex();
            aux = endPositions.get(i);
            if (aux == null) {
                aux = new HashSet<ParsedSymbol>();
                endPositions.put(i,aux);
            }
            aux.add(p);
        }

        elementRules = new HashMap<Object,Set<Rule>>();
        Iterator<Rule> iter;
        Rule r;
        Object e;
        Set<Rule> sr;
        for (iter = grammar.getRules().iterator();iter.hasNext();) {
            r = iter.next();
            e = r.getLeft().getType();
            sr = elementRules.get(e);
            if (sr == null) {
                sr = new HashSet<Rule>();
                elementRules.put(e,sr);
            }
            sr.add(r);
        }
    }


    /**
     * @return the start symbols of this graph.
     */
    public Set<ParsedSymbol> getStart() {
        return Collections.unmodifiableSet(start);
    }


    /**
     * @return the symbols used in this graph.
     */
    public Set<ParsedSymbol> getSymbols() {
        return Collections.unmodifiableSet(symbols);
    }

    /**
     * @return the map of precedings.
     */
    public Map<ParsedSymbol, Set<ParsedSymbol>> getPreceding() {
        return Collections.unmodifiableMap(preceding);
    }

    /**
     * @return the map of followings.
     */
    public Map<ParsedSymbol, Set<ParsedSymbol>> getFollowing() {
        return Collections.unmodifiableMap(following);
    }

    /**
     * @return the map of start positions.
     */
    public Map<Integer,Set<ParsedSymbol>> getStartPositions() {
        return Collections.unmodifiableMap(startPositions);
    }

    /**
     * @return the map of end positions.
     */
    public Map<Integer,Set<ParsedSymbol>> getEndPositions() {
        return Collections.unmodifiableMap(endPositions);
    }

    /**
     * @return the map of rules that generate an element
     */
    public Map<Object, Set<Rule>> getElementRules() {
        return Collections.unmodifiableMap(elementRules);
    }

    /**
     * @return the grammar
     */
    public Grammar getGrammar() {
        return grammar;
    }

}
