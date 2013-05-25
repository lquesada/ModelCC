/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import java.io.Serializable;
import java.util.List;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.AssociativityType;
import org.modelcc.metamodel.ModelElement;

/**
 * Basic element.
 * @author elezeta
 * @serial
 */
public final class BasicModelElement extends ModelElement implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Pattern.
     */
    private PatternRecognizer pattern;

    /**
     * Value field.
     */
    private String valueField;

    /**
     * Constructor.
     * @param elementClass the element class
     * @param associativity the associativity
     * @param prefix the prefix
     * @param suffix the suffix
     * @param separator the default separator
     * @param AutorunMethod the run on load method
     * @param pattern the pattern
     * @param valueField the value field
     * @param hasAnyAssociativity has any associativity 
     */
    public BasicModelElement(Class elementClass,AssociativityType associativity,List<PatternRecognizer> prefix,List<PatternRecognizer> suffix,List<PatternRecognizer> separator,String AutorunMethod,PatternRecognizer pattern,String valueField,boolean hasAnyAssociativity) {
        super(elementClass,associativity,prefix,suffix,separator,AutorunMethod,hasAnyAssociativity);
        this.pattern = pattern;
        this.valueField = valueField;
    }

    /**
     * @return the pattern
     */
    public PatternRecognizer getPattern() {
        return pattern;
    }

    /**
     * @return the value field
     */
    public String getValueField() {
        return valueField;
    }

}
