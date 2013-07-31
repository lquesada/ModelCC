/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import java.util.List;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.probabilistic.ProbabilityEvaluator;

/**
 * ElementMember with multiplicity.
 * @author elezeta
 * @serial
 */
public final class MultipleElementMember extends ElementMember {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Collection type.
     */
    private CollectionType collection;

    /**
     * Minimum multiplicity.
     */
    private int minimumMultiplicity;

    /**
     * Maximum multiplicity
     */
    private int maximumMultiplicity;

    /**
     * Constructor
     * @param field the content field
     * @param elementClass the c ontent element class
     * @param optional whether if this content is optional or not
     * @param id is id
     * @param reference is reference
     * @param prefix the content prefix
     * @param suffix the content suffix
     * @param separator the ad hoc separator
     * @param collection the collection type
     * @param minimumMultiplicity the minimum multiplicity
     * @param maximumMultiplicity the maximum multiplicity
     * @param probabilityEvaluator probability evaluator
     */
    public MultipleElementMember(String field,Class elementClass,boolean optional,boolean id,boolean reference,List<PatternRecognizer> prefix,List<PatternRecognizer> suffix,List<PatternRecognizer> separator,CollectionType collection,int minimumMultiplicity,int maximumMultiplicity,ProbabilityEvaluator probabilityEvaluator) {
        super(field,elementClass,optional,id,reference,prefix,suffix,separator,probabilityEvaluator);
        this.collection = collection;
        this.minimumMultiplicity = minimumMultiplicity;
        this.maximumMultiplicity = maximumMultiplicity;
    }

    /**
     * @return the collection type
     */
    public CollectionType getCollection() {
        return collection;
    }

    /**
     * @return the minimum multiplicity
     */
    public int getMinimumMultiplicity() {
        return minimumMultiplicity;
    }

    /**
     * @return the maximum multiplicity
     */
    public int getMaximumMultiplicity() {
        return maximumMultiplicity;
    }

}
