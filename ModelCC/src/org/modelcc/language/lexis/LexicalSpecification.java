/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.lexis;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Lamb specification.
 * @author elezeta
 * @serial
 */
public final class LexicalSpecification implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * List of sorted token specifications.
     */
    private List<TokenSpecification> tspecs;

    /**
     * Map of precedences.
     */
    private Map<TokenSpecification,Set<TokenSpecification>> precedences;

    /**
     * Constructor.
     * @param tspecs the token specification list.
     * @param precedes the token precedence map.
     */
    public LexicalSpecification(List<TokenSpecification> tspecs,Map<TokenSpecification,Set<TokenSpecification>> precedes) {
        this.tspecs = tspecs;
        this.precedences = precedes;
    }
    
    /**
     * @return the token specification list.
     */
    public List<TokenSpecification> getTokenSpecifications() {
        return Collections.unmodifiableList(tspecs);
    }

    /**
     * @return the map of precedences.
     */
    public Map<TokenSpecification, Set<TokenSpecification>> getPrecedences() {
        return Collections.unmodifiableMap(precedences);
    }

}
