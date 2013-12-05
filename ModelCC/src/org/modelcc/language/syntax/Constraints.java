/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * Syntactic constraints.
 * @author elezeta
 * @serial
 */
public final class Constraints implements Serializable {

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
     * Map of start precedences.
     */
    private Map<Rule, Set<Rule>> startPrecedences;

    /**
     * Constructor.
     * @param associativities the elements associativities.
     * @param compositionPrecedences the map of content precedences.
     * @param selectionPrecedences the map of selection precedences.
     * @param startPrecedences the map of start precedences.
     */
    public Constraints(Map<Object,AssociativityConstraint> associativities, Map<Rule,Set<Rule>> compositionPrecedences,Map<Rule, Set<Rule>> selectionPrecedences,Map<Rule, Set<Rule>> startPrecedences) {
        this.associativities = associativities;
        this.compositionPrecedences = compositionPrecedences;
        this.selectionPrecedences = selectionPrecedences;
        this.startPrecedences = startPrecedences;
    }

    /**
     * @return the object associativities.
     */
    public Map<Object,AssociativityConstraint> getAssociativities() {
        return Collections.unmodifiableMap(associativities);
    }

    /**
     * @return the composition precedences
     */
    public Map<Rule, Set<Rule>> getCompositionPrecedences() {
        return Collections.unmodifiableMap(compositionPrecedences);
    }

    /**
     * @return the selection precedences
     */
    public Map<Rule, Set<Rule>> getSelectionPrecedences() {
        return Collections.unmodifiableMap(selectionPrecedences);
    }

    /**
     * @return the selection precedences
     */
    public Map<Rule, Set<Rule>> getStartPrecedences() {
        return Collections.unmodifiableMap(startPrecedences);
    }

}
