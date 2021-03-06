/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import java.io.Serializable;
import java.util.List;

import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.probabilistic.ProbabilityEvaluator;

/**
 * Element content
 * @author elezeta
 * @serial
 */
public class ElementMember implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Field.
     */
    private String field;

    /**
     * Element class.
     */
    private Class elementClass;

    /**
     * Whether if this content is optional.
     */
    private boolean optional;

    /**
     * Whether if this content is id.
     */
    private boolean id;

    /**
     * Whether if this content is a reference.
     */
    private boolean reference;

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
     * Probability evaluator
     */
    private ProbabilityEvaluator probabilityEvaluator;
    
    /**
     * Constructor
     * @param field the field
     * @param elementClass the element class.
     * @param optional whether if this content is optional or not
     * @param id is id
     * @param reference is reference
     * @param prefix the prefix
     * @param suffix the suffix
     * @param separator the ad hoc separator
     * @param positionTag the position tag
     * @param probabilityEvaluator probability evaluator
     */
    public ElementMember(String field,Class elementClass,boolean optional,boolean id,boolean reference,List<PatternRecognizer> prefix,List<PatternRecognizer> suffix,List<PatternRecognizer> separator,ProbabilityEvaluator probabilityEvaluator) {
        this.field = field;
        this.elementClass = elementClass;
        this.optional = optional;
        this.id = id;
        this.reference = reference;
        this.prefix = prefix;
        this.suffix = suffix;
        this.separator = separator;
        this.probabilityEvaluator = probabilityEvaluator; 
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @return the element class
     */
    public Class getElementClass() {
        return elementClass;
    }

    /**
     * @return whether if this element is optional
     */
    public boolean isOptional() {
        return optional;
    }

	/**
     * @return the prefix
     */
    public List<PatternRecognizer> getPrefix() {
        return prefix;
    }

    /**
     * @return the suffix
     */
    public List<PatternRecognizer> getSuffix() {
        return suffix;
    }

    /**
     * @return the ad hoc separator
     */
    public List<PatternRecognizer> getSeparator() {
        return separator;
    }

    /**
     * @return is id
     */
    public boolean isId() {
        return id;
    }

    /**
     * @return is reference
     */
    public boolean isReference() {
        return reference;
    }
    
    /**
     * @return the probability evaluator 
     */
    public ProbabilityEvaluator getProbabilityEvaluator() {
    	return probabilityEvaluator;
    }

	public void setPrefix(List<PatternRecognizer> prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(List<PatternRecognizer> suffix) {
		this.suffix = suffix;
	}

	public void setSeparator(List<PatternRecognizer> separator) {
		this.separator = separator;
	}


    
}
