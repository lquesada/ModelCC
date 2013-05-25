/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 * Language model.
 * @author elezeta
 * @serial
 */
public final class Model implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Set of elements of this model.
     */
    private Set<ModelElement> elements;

    /**
     * The start element of this model.
     */
    private ModelElement start;

    /**
     * Delimiters of this model.
     */
    private Set<PatternRecognizer> delimiters;

    /**
     * ModelElement precedence map.
     */
    private Map<ModelElement,Set<ModelElement>> precedences;

    /**
     * ModelElement subelement map.
     */
    private Map<ModelElement,Set<ModelElement>> subelements;

    /**
     * ModelElement superclass map.
     */
    private Map<ModelElement,ModelElement> superelements;

    /**
     * Class to element map.
     */
    private Map<Class,ModelElement> classToElement;

    /**
     * Constructor.
     * @param elements the set of elements of this model.
     * @param start the start element of this model
     * @param delimiters the delimiters of this model
     * @param precedences the element precedence map
     * @param subelements the element subclass map
     * @param superelements the element superclass map
     * @param classToElement the class to element map
     */
    public Model(Set<ModelElement> elements,ModelElement start,Set<PatternRecognizer> delimiters,Map<ModelElement,Set<ModelElement>> precedences,Map<ModelElement,Set<ModelElement>> subelements,Map<ModelElement,ModelElement> superelements,Map<Class,ModelElement> classToElement) {
        this.elements = elements;
        this.start = start;
        this.delimiters = delimiters;
        this.precedences = precedences;
        this.subelements = subelements;
        this.superelements = superelements;
        this.classToElement = classToElement;
    }

    /**
     * @return the elements of this model
     */
    public Set<ModelElement> getElements() {
        return Collections.unmodifiableSet(elements);
    }

    /**
     * @return the start element of this model
     */
    public ModelElement getStart() {
        return start;
    }

    /**
     * @return the delimiters of this model
     */
    public Set<PatternRecognizer> getDelimiters() {
        return Collections.unmodifiableSet(delimiters);
    }

    /**
     * @return the element precedences map
     */
    public Map<ModelElement, Set<ModelElement>> getPrecedences() {
        return Collections.unmodifiableMap(precedences);
    }

    /**
     * @return the element subelements map
     */
    public Map<ModelElement, Set<ModelElement>> getSubelements() {
        return Collections.unmodifiableMap(subelements);
    }

    /**
     * @return the element superelements map
     */
    public Map<ModelElement,ModelElement> getSuperelements() {
        return Collections.unmodifiableMap(superelements);
    }

    /**
     * @return the classToElement
     */
    public Map<Class, ModelElement> getClassToElement() {
        return Collections.unmodifiableMap(classToElement);
    }

}
