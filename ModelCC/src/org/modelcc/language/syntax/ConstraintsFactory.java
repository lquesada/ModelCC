/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.modelcc.language.syntax.CyclicCompositionPrecedenceException;
import org.modelcc.language.syntax.AssociativityConstraint;
import org.modelcc.language.syntax.Constraints;
import org.modelcc.language.syntax.CyclicSelectionPrecedenceException;


/**
 * Constraints Factory.
 * @author elezeta
 * @serial
 */
public final class ConstraintsFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Map of associativities.
     */
    private Map<Object,AssociativityConstraint> associativities;

    /**
     * Map of composition precedences.
     */
    private Map<Rule, Set<Rule>> compositionPrecedences;

    /**
     * Map of selection precedences.
     */
    private Map<Rule, Set<Rule>> selectionPrecedences;

    /**
     * Default constructor.
     */
    public ConstraintsFactory() {
        associativities = new HashMap<Object,AssociativityConstraint>();
        compositionPrecedences = new HashMap<Rule, Set<Rule>>();
        selectionPrecedences = new HashMap<Rule, Set<Rule>>();
    }

    /**
     * Set an associativity constraint for an object type.
     * @param type the object type.
     * @param as the associativity constraint.
     */
    public void setAssociativity(Object type,AssociativityConstraint as) {
        if (as==AssociativityConstraint.UNDEFINED)
            associativities.remove(type);
        else
            associativities.put(type,as);
    }

    /**
     * @return the associativities
     */
    public Map<Object, AssociativityConstraint> getAssociativities() {
        return Collections.unmodifiableMap(associativities);
    }

    /**
     * @return the composition precedences.
     */
    public Map<Rule, Set<Rule>> getCompositionPrecedences() {
        return Collections.unmodifiableMap(compositionPrecedences);
    }

    /**
     * Adds a composition precedence between rules.
     * @param ts1 the rule that precedes.
     * @param ts2 the rule that is preceded.
     */
    public void addCompositionPrecedences(Rule ts1,Rule ts2) {
        if (ts1 != null && ts2 != null) {
            Set<Rule> set = compositionPrecedences.get(ts1);
            if (set == null) {
                set = new HashSet<Rule>();
                compositionPrecedences.put(ts1,set);
            }
            set.add(ts2);
        }
    }

    /**
     * Removes a composition precedence between rules.
     * @param ts1 the rule that precedes.
     * @param ts2 the rule that is preceded.
     */
    public void removeCompositionPrecedences(Rule ts1,Rule ts2) {
        if (ts1 != null && ts2 != null) {
            Set<Rule> set = compositionPrecedences.get(ts1);
            if (set == null) {
                return;
            }
            set.remove(ts2);
            if (set.isEmpty())
                compositionPrecedences.remove(ts1);
        }
    }

    /**
     * @return the selection precedences
     */
    public Map<Rule, Set<Rule>> getSelectionPrecedences() {
        return Collections.unmodifiableMap(selectionPrecedences);
    }

    /**
     * Adds an selection precedence between rules.
     * @param ts1 the rule that precedes.
     * @param ts2 the rule that is preceded.
     */
    public void addSelectionPrecedences(Rule ts1,Rule ts2) {
        if (ts1 != null && ts2 != null) {
            Set<Rule> set = selectionPrecedences.get(ts1);
            if (set == null) {
                set = new HashSet<Rule>();
                selectionPrecedences.put(ts1,set);
            }
            set.add(ts2);
        }
    }

    /**
     * Removes an selection precedence between rules.
     * @param ts1 the rule that precedes.
     * @param ts2 the rule that is preceded.
     */
    public void removeSelectionPrecedences(Rule ts1,Rule ts2) {
        if (ts1 != null && ts2 != null) {
            Set<Rule> set = selectionPrecedences.get(ts1);
            if (set == null) {
                return;
            }
            set.remove(ts2);
            if (set.isEmpty())
                selectionPrecedences.remove(ts1);
        }
    }

    /**
     * Generates a syntactic specification.
     * @throws CyclicCompositionPrecedenceException whenever several rules mutually precede with a composition precedence.
     * @throws CyclicSelectionPrecedenceException whenever several rules mutually precede with an selection precedence.
     * @return the syntactic specification.
     */
    public Constraints create() throws CyclicCompositionPrecedenceException, CyclicSelectionPrecedenceException {
        Set<Rule> pool = new HashSet<Rule>();

        // -------------
        // Check cyclic start compositionPrecedences
        // -------------

        {

            // INITIALIZATION
            // --------------

            // Rules preceded by any rule in the pool.
            Set<Rule> precededs;

            // Auxiliar variables.
            Iterator<Rule> ite;
            Rule r;
            Entry<Rule, Set<Rule>> e;
            Iterator<Entry<Rule, Set<Rule>>> eite;


            // Whether if any new matcher has been added to the sorted list.
            boolean found;

            // List of conflicting elements.
            String list;

            // PROCEDURE
            // --------------

            for (eite = compositionPrecedences.entrySet().iterator();eite.hasNext();) {
                e = eite.next();
                pool.add(e.getKey());
                for (ite = e.getValue().iterator();ite.hasNext();) {
                    pool.add(ite.next());
                }
            }

            while (!pool.isEmpty()) {

                found = false;

                // Update precededs list.
                precededs = new HashSet<Rule>();
                for (ite = pool.iterator();ite.hasNext();) {
                    r = ite.next();
                    if (compositionPrecedences.get(r) != null)
                        precededs.addAll(compositionPrecedences.get(r));
                }

                // Adds news unprecededs.
                for (ite = pool.iterator();ite.hasNext();) {
                    r = ite.next();
                    if (!precededs.contains(r)) {
                        ite.remove();
                        found = true;
                    }
                }

                if (!found) {
                    list = new String();
                    for (ite = pool.iterator();ite.hasNext();)
                        list += " "+ite.next().getLeft().getType();
                    throw new CyclicCompositionPrecedenceException("Cyclic precedence exception:"+list+".");
                }
            }
        }

        // -------------
        // Check cyclic selection precedences
        // -------------

        {

            // INITIALIZATION
            // --------------

            // Rules preceded by any rule in the pool.
            Set<Rule> precededs;

            // Auxiliar variables.
            Iterator<Rule> ite;
            Rule r;
            Entry<Rule, Set<Rule>> e;
            Iterator<Entry<Rule, Set<Rule>>> eite;


            // Whether if any new matcher has been added to the sorted list.
            boolean found;

            // List of conflicting elements.
            String list;

            // PROCEDURE
            // --------------

            for (eite = selectionPrecedences.entrySet().iterator();eite.hasNext();) {
                e = eite.next();
                pool.add(e.getKey());
                for (ite = e.getValue().iterator();ite.hasNext();) {
                    pool.add(ite.next());
                }
            }

            while (!pool.isEmpty()) {

                found = false;

                // Update precededs list.
                precededs = new HashSet<Rule>();
                for (ite = pool.iterator();ite.hasNext();) {
                    r = ite.next();
                    if (selectionPrecedences.get(r) != null)
                        precededs.addAll(selectionPrecedences.get(r));
                }

                // Adds news unprecededs.
                for (ite = pool.iterator();ite.hasNext();) {
                    r = ite.next();
                    if (!precededs.contains(r)) {
                        ite.remove();
                        found = true;
                    }
                }

                if (!found) {
                    list = new String();
                    for (ite = pool.iterator();ite.hasNext();)
                        list += " "+ite.next().getLeft().getType();
                    throw new CyclicSelectionPrecedenceException("Cyclic precedence exception:"+list+".");
                }
            }
        }

        return new Constraints(associativities,compositionPrecedences,selectionPrecedences);

    }


}
