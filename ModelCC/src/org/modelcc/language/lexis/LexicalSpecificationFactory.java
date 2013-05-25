/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.lexis;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Lamb specification factory.
 * @author elezeta
 * @serial
 */
public final class LexicalSpecificationFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Set of token specifications.
     */
    private Set<TokenSpecification> tspecs;

    /**
     * Map of precedences.
     */
    private Map<TokenSpecification, Set<TokenSpecification>> precedences;

    /**
     * Default constructor.
     */
    public LexicalSpecificationFactory() {
        tspecs = new HashSet<TokenSpecification>();
        precedences = new HashMap<TokenSpecification,Set<TokenSpecification>>();
    }

    /**
     * Adds a token specification.
     * @param ts the token specification to add.
     */
    public void addTokenSpecification(TokenSpecification ts) {
        if (ts != null)
            tspecs.add(ts);
    }

    /**
     * Removes a token specification.
     * @param ts the token specification to remove.
     */
    public void removeTokenSpecification(TokenSpecification ts) {
        tspecs.remove(ts);
    }

    /**
     * Adds a precedence relationship between tokens.
     * @param ts1 the token that precedes.
     * @param ts2 the token that is preceded.
     */
    public void addPrecedence(TokenSpecification ts1,TokenSpecification ts2) {
        Set<TokenSpecification> set = precedences.get(ts1);
        if (set == null) {
            set = new HashSet<TokenSpecification>();
            precedences.put(ts1,set);
        }
        set.add(ts2);
    }

    /**
     * Removes a precedence relationship between tokens.
     * @param ts1 the token that precedes.
     * @param ts2 the token that is preceded.
     */
    public void removePrecedence(TokenSpecification ts1,TokenSpecification ts2) {
        Set<TokenSpecification> set = precedences.get(ts1);
        if (set == null) {
            return;
        }
        set.remove(ts2);
        if (set.isEmpty())
            precedences.remove(ts1);
    }

    /**
     * @return the token specification set.
     */
    public Set<TokenSpecification> getTokenSpecifications() {
        return Collections.unmodifiableSet(tspecs);
    }

    /**
     * @return the precedences map.
     */
    public Map<TokenSpecification, Set<TokenSpecification>> getPrecedences() {
        return Collections.unmodifiableMap(precedences);
    }

    /**
     * Creates a lexical specification.
     * @throws TokenSpecificationCyclicPrecedenceException whenever several tokens mutually precede.
     * @return the lexical Specification.
     */
    public LexicalSpecification create() throws TokenSpecificationCyclicPrecedenceException {
        List<TokenSpecification> stspecs = new ArrayList<TokenSpecification>();
        Set<TokenSpecification> pool = new HashSet<TokenSpecification>();
        pool.addAll(tspecs);

        
        // -------------
        // Generate specification
        // -------------

        {

            // INITIALIZATION
            // --------------

            // Token specifications preceded by any token specification in the pool.
            Set<TokenSpecification> precededs;

            // Auxiliar variables.
            Iterator<TokenSpecification> ite;
            TokenSpecification ts;
            Set<TokenSpecification> pset;

            // Whether if any new token specification has been added to the sorted list.
            boolean found;

            // List of conflicting elements.
            String list;

            // PROCEDURE
            // --------------

            while (!pool.isEmpty()) {

                found = false;

                // Update precededs list.
                precededs = new HashSet<TokenSpecification>();
                for (ite = pool.iterator();ite.hasNext();) {
                    ts = ite.next();
                    pset = precedences.get(ts);
                    if (pset != null) {
                        precededs.addAll(pset);
                    }
                }

                // Adds news unprecededs.
                for (ite = pool.iterator();ite.hasNext();) {
                    ts = ite.next();
                    if (!precededs.contains(ts)) {
                        stspecs.add(ts);
                        ite.remove();
                        found = true;
                    }
                }

                if (!found) {
                    list = new String();
                    for (ite = pool.iterator();ite.hasNext();)
                        list += " "+ite.next().getType();
                    throw new TokenSpecificationCyclicPrecedenceException("Cyclic precedence exception:"+list+".");
                }
            }
        }

        return new LexicalSpecification(stspecs, precedences);
    }

}
