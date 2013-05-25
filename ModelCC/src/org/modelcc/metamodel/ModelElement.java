/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import org.modelcc.AssociativityType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.modelcc.lexer.recognizer.PatternRecognizer;

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
     * Run on load method.
     */
    private String AutorunMethod;

    /**
     * has any associativity.
     */
    private boolean hasAnyAssociativity;

    /**
     * Constructor.
     * @param elementClass the element class
     * @param associativity the associativity
     * @param prefix the prefix
     * @param suffix the suffix
     * @param separator the default separator
     * @param AutorunMethod the run on load method
     * @param hasAnyAssociativity has any associativity 
     */
    public ModelElement(Class elementClass,AssociativityType associativity,List<PatternRecognizer> prefix,List<PatternRecognizer> suffix,List<PatternRecognizer> separator,String AutorunMethod,boolean hasAnyAssociativity) {
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
        this.AutorunMethod = AutorunMethod;
        this.hasAnyAssociativity = hasAnyAssociativity;

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
     * @return the run on load method
     */
    public String getAutorunMethod() {
        return AutorunMethod;
    }

    /**
     * @return the hasAnyAssociativity
     */
    public boolean getHasAnyAssociativity() {
        return hasAnyAssociativity;
    }

}
