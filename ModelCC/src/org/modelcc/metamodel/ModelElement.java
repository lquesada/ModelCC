/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import org.modelcc.AssociativityType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.probabilistic.ProbabilityEvaluator;

/**
 * ModelElement.
 * @author elezeta
 * @serial
 */
public abstract class ModelElement implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * ModelElement class.
     */
    private Class elementClass;

    /**
     * Associativity type.
     */
    private AssociativityType associativity;

    /**
     * Prefix.
     */
    private List<PatternRecognizer> prefix;

    /**
     * Suffix.
     */
    private List<PatternRecognizer> suffix;

    /**
     * Separator.
     */
    private List<PatternRecognizer> separator;

    /**
     * Setup method.
     */
    private String setupMethod;

    /**
     * Constraint methods.
     */
    private List<String> constraintMethods;

    /**
     * has any associativity.
     */
    private boolean hasAnyAssociativity;

    /**
     * Positions.
     */
    private Map<ElementMember, PositionInfo> positions;
    
    /** 
     * Probability evaluator
     */
    private ProbabilityEvaluator probabilityEvaluator;
    
    /**
     * Constructor.
     * @param elementClass the element class
     * @param associativity the associativity
     * @param prefix the prefix
     * @param suffix the suffix
     * @param separator the default separator
     * @param setupMethod the setup method
     * @param constraintMethod the constraint methods
     * @param hasAnyAssociativity has any associativity 
     * @param probabilityEvaluator probability evaluator
     */
    public ModelElement(Class elementClass,AssociativityType associativity,List<PatternRecognizer> prefix,List<PatternRecognizer> suffix,List<PatternRecognizer> separator,String setupMethod,List<String> constraintMethods,boolean hasAnyAssociativity,ProbabilityEvaluator probabilityEvaluator) {
        this.elementClass = elementClass;
        this.associativity = associativity;
        this.prefix = prefix;
        if (prefix == null)
            this.prefix = new ArrayList<PatternRecognizer>();
        this.suffix = suffix;
        if (suffix == null)
            this.suffix = new ArrayList<PatternRecognizer>();
        this.separator = separator;
        if (separator == null)
            this.separator = new ArrayList<PatternRecognizer>();
        this.setupMethod = setupMethod;
        this.constraintMethods = constraintMethods;
        this.hasAnyAssociativity = hasAnyAssociativity;
        this.probabilityEvaluator = probabilityEvaluator;
    }

    /**
     * @return the element class
     */
    public Class getElementClass() {
        return elementClass;
    }

    /**
     * @return the associativity
     */
    public AssociativityType getAssociativity() {
        return associativity;
    }

    /**
     * @return the prefix
     */
    public List<PatternRecognizer> getPrefix() {
        return Collections.unmodifiableList(prefix);
    }

    /**
     * @return the suffix
     */
    public List<PatternRecognizer> getSuffix() {
        return Collections.unmodifiableList(suffix);
    }

    /**
     * @return the separator
     */
    public List<PatternRecognizer> getSeparator() {
        return Collections.unmodifiableList(separator);
    }

    /**
     * @return the setup method
     */
    public String getSetupMethod() {
        return setupMethod;
    }

    /**
     * @return the setup method
     */
    public List<String> getConstraintMethods() {
        return Collections.unmodifiableList(constraintMethods);
    }

    /**
     * @return the hasAnyAssociativity
     */
    public boolean getHasAnyAssociativity() {
        return hasAnyAssociativity;
    }

	public void setPositions(Map<ElementMember, PositionInfo> positions) {
		this.positions = positions;
	}
	
	public Map<ElementMember, PositionInfo> getPositions() {
		return Collections.unmodifiableMap(positions);
	}

    /**
     * @return the probability evaluator 
     */
    public ProbabilityEvaluator getProbabilityEvaluator() {
    	return probabilityEvaluator;
    }

}
