/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.io.java;

import org.modelcc.metamodel.ElementMember;
import org.modelcc.CompositionType;
import org.modelcc.AssociativityType;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 * Pre Element.
 * @author elezeta
 * @serial
 */
public final class PreElement implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    /**
     * Element class.
     */
    private Class elementClass;
    /**
     * Element contents.
     */
    private List<ElementMember> contents;
    /**
     * Element ids.
     */
    private List<ElementMember> ids;
    /**
     * Whether if the element can be used in free order.
     */
    private Boolean freeOrder;
    /**
     * Associativity type.
     */
    private AssociativityType associativity;
    /**
     * Composition type.
     */
    private CompositionType composition;
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
     * Pattern.
     */
    private PatternRecognizer pattern;
    /**
     * Value field.
     */
    private Field valueField;
    /**
     * Run on load method.
     */
    private Method AutorunMethod;

    /**
     * Any subclass has associativity.
     */
    private boolean hasAnyAssociativity;
    
    /**
     * Constructor
     * @param elementClass the element class
     * @param contents the contents
     * @param ids the ids
     * @param freeOrder whether if this element may be freeOrder
     * @param associativity the associativity
     * @param composition the composition
     * @param prefix the prefix
     * @param suffix the suffix
     * @param separator the separator
     * @param pattern the pattern
     * @param valueField the value field
     * @param AutorunMethod the run on load method
     */
    public PreElement(Class elementClass, List<ElementMember> contents, List<ElementMember> ids, Boolean freeOrder, AssociativityType associativity, CompositionType composition, List<PatternRecognizer> prefix, List<PatternRecognizer> suffix, List<PatternRecognizer> separator, PatternRecognizer pattern, Field valueField, Method AutorunMethod) {
        this.elementClass = elementClass;
        this.contents = contents;
        this.ids = ids;
        this.freeOrder = freeOrder;
        this.associativity = associativity;
        this.composition = composition;
        this.prefix = prefix;
        this.suffix = suffix;
        this.separator = separator;
        this.pattern = pattern;
        this.valueField = valueField;
        this.AutorunMethod = AutorunMethod;
        this.hasAnyAssociativity = false;
    }

    /**
     * @return the element class
     */
    public Class getElementClass() {
        return elementClass;
    }

    /**
     * @return the contents
     */
    public List<ElementMember> getContents() {
        return contents;
    }

    /**
     * @return whether if this element can be freeOrder
     */
    public Boolean isFreeOrder() {
        return freeOrder;
    }

    /**
     * @return the associativity
     */
    public AssociativityType getAssociativity() {
        return associativity;
    }

    /**
     * @return the composition
     */
    public CompositionType getComposition() {
        return composition;
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
     * @return the separator
     */
    public List<PatternRecognizer> getSeparator() {
        return separator;
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
    public Field getValueField() {
        return valueField;
    }

    /**
     * @return the run on load method
     */
    public Method getAutorunMethod() {
        return AutorunMethod;
    }

    /**
     * @param elementClass the elementClass to set
     */
    public void setElementClass(Class elementClass) {
        this.elementClass = elementClass;
    }

    /**
     * @param contents the contents to set
     */
    public void setContents(List<ElementMember> contents) {
        this.contents = contents;
    }

    /**
     * @param freeOrder whether if this element contents may be freeOrder or not
     */
    public void setFreeOrder(boolean freeOrder) {
        this.freeOrder = freeOrder;
    }

    /**
     * @param associativity the associativity type to set
     */
    public void setAssociativity(AssociativityType associativity) {
        this.associativity = associativity;
    }

    /**
     * @param composition the composition type to set
     */
    public void setComposition(CompositionType composition) {
        this.composition = composition;
    }

    /**
     * @param prefix the prefix to set
     */
    public void setPrefix(List<PatternRecognizer> prefix) {
        this.prefix = prefix;
    }

    /**
     * @param suffix the suffix to set
     */
    public void setSuffix(List<PatternRecognizer> suffix) {
        this.suffix = suffix;
    }

    /**
     * @param separator the separator to set
     */
    public void setSeparator(List<PatternRecognizer> separator) {
        this.separator = separator;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(PatternRecognizer pattern) {
        this.pattern = pattern;
    }

    /**
     * @param valueField the value field to set
     */
    public void setValueField(Field valueField) {
        this.valueField = valueField;
    }

    /**
     * @param AutorunMethod the run on load method to set
     */
    public void setAutorunMethod(Method AutorunMethod) {
        this.AutorunMethod = AutorunMethod;
    }

    /**
     * @return the hasAnyAssociativity
     */
    public boolean getHasAnyAssociativity() {
        return hasAnyAssociativity;
    }

    /**
     * @param hasAnyAssociativity the hasAnyAssociativity to set
     */
    public void setHasAnyAssociativity(boolean hasAnyAssociativity) {
        this.hasAnyAssociativity = hasAnyAssociativity;
    }

    /**
     * @return the ids
     */
    public List<ElementMember> getIds() {
        return ids;
    }

    /**
     * @param ids the ids to set
     */
    public void setIds(List<ElementMember> ids) {
        this.ids = ids;
    }
    
}
