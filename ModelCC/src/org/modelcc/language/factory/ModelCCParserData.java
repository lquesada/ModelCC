/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.modelcc.parser.fence.Symbol;


/**
 * ModelCC Parser Data.
 * @author elezeta
 * @serial
 */
public final class ModelCCParserData implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * ID maps.
     */
    private Map<Class,Map<KeyWrapper,Object>> ids;

    /**
     * Object map.
     */ 
    private Map<Object,ObjectWrapper> map;
          
    /**
     * The lazy references set.
     */
    private Set<Symbol> lazyReferences;
    
    /**
     * Constructor.
     */
    public ModelCCParserData() {
        ids = new HashMap<Class,Map<KeyWrapper,Object>>();
        map = new HashMap<Object,ObjectWrapper>();
        lazyReferences = new HashSet<Symbol>();
    }

    /**
     * @return the ids
     */
    public Map<Class,Map<KeyWrapper,Object>> getIds() {
        return ids;
    }

    /**
     * @return the map
     */
    public Map<Object,ObjectWrapper> getMap() {
        return map;
    }

    /**
     * @return the lazyReferences
     */
    public Set<Symbol> getLazyReferences() {
        return lazyReferences;
    }
    
}