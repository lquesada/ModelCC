/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.probabilistic.ProbabilityEvaluator;
import org.modelcc.AssociativityType;
import org.modelcc.CompositionType;

/**
 * Composite element.
 * @author elezeta
 * @serial
 */
public final class ComplexModelElement extends ModelElement {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * ModelElement contents.
     */
    private List<ElementMember> contents;

    /**
     * ModelElement ids.
     */
    private List<ElementMember> ids;

    /**
     * Whether if the element can be placed in free order.
     */
    private boolean freeOrder;

    /**
     * Composition type.
     */
    private CompositionType composition;

    /**
     * Field to ElementMember.
     */
    private Map<String,ElementMember> fieldToContent;

    /**
     * Constructor.
     * @param elementClass the element class
     * @param associativity the associativity
     * @param prefix the prefix
     * @param suffix the suffix
     * @param separator the default separator
     * @param setupMethod the setup method
     * @param constraintMethods the constraint checking methods
     * @param contents the element contents
     * @param ids the element ids
     * @param freeOrder whether if the element may be placed in free order
     * @param composition the composition type
     * @param hasAnyAssociativity has any associativity 
     * @param probabilityEvaluator probability evaluator
     */
    public ComplexModelElement(Class elementClass,AssociativityType associativity,List<PatternRecognizer> prefix,List<PatternRecognizer> suffix,List<PatternRecognizer> separator,String setupMethod,List<String> constraintMethods,List<ElementMember> contents,List<ElementMember> ids,boolean freeOrder,CompositionType composition,boolean hasAnyAssociativity,ProbabilityEvaluator probabilityEvaluator) {
        super(elementClass,associativity,prefix,suffix,separator,setupMethod,constraintMethods,hasAnyAssociativity,probabilityEvaluator);
        this.contents = contents;
        this.ids = ids;
        this.freeOrder = freeOrder;
        this.composition = composition;
        fieldToContent = new HashMap<String,ElementMember>();
        ElementMember ct;
        for (Iterator<ElementMember> ite = contents.iterator();ite.hasNext();) {
            ct = ite.next();
            fieldToContent.put(ct.getField(),ct);
        }
    }

    /**
     * @return the contents
     */
    public List<ElementMember> getContents() {
        return Collections.unmodifiableList(contents);
    }

    /**
     * @return the ids
     */
    public List<ElementMember> getIds() {
        return Collections.unmodifiableList(ids);
    }

    /**
     * @return whether if this element can be freeOrder
     */
    public boolean isFreeOrder() {
        return freeOrder;
    }

    /**
     * @return the composition
     */
    public CompositionType getComposition() {
        return composition;
    }

    /**
     * @return the fieldToContent
     */
    public Map<String, ElementMember> getFieldToContent() {
        return Collections.unmodifiableMap(fieldToContent);
    }

    
}
