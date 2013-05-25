/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.List;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.metamodel.ModelElement;

/**
 *
 * @author elezeta
 * @serial
 */
public class ElementId implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * ModelElement type.
     */
    private ElementType type;

    /**
     * ModelElement.
     */
    private ModelElement element;

    /**
     * Separator list.
     */
    private List<PatternRecognizer> listSeparator;

    /**
     * Reference.
     */
    private boolean reference;
    
    /**
     * Constructor
     * @param type the element type
     * @param element the element
     * @param listSeparator the separator list
     * @param reference whether if this element is a reference. 
     */
    public ElementId(ElementType type,ModelElement element,List<PatternRecognizer> listSeparator,boolean reference) {
        this.type = type;
        this.element = element;
        this.listSeparator = listSeparator;
        this.reference = reference;
    }

    /**
     * @return the element type
     */
    public ElementType getType() {
        return type;
    }

    /**
     * @return the element
     */
    public ModelElement getElement() {
        return element;
    }

    /**
     * @return the separator list
     */
    public List<PatternRecognizer> getListSeparator() {
        return listSeparator;
    }

    @Override
    public String toString() {
        String r = "";
        if (reference)
            r = "&";
        switch (type) {
            case ELEMENT:
                return r+"ELEMENT("+getElement().getElementClass().getSimpleName()+")";
            case LIST:
                return r+"LIST("+getElement().getElementClass().getSimpleName()+")";
            case LISTZERO:
                return r+"LISTZERO("+getElement().getElementClass().getSimpleName()+")";
            case BASIC:
            default:
                return r+"BASIC("+getElement().getElementClass().getSimpleName()+")";
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ElementId other = (ElementId) obj;
        if (this.type != other.type) {
            return false;
        }
        if (this.element != other.element && (this.element == null || !this.element.equals(other.element))) {
            return false;
        }
        if (this.listSeparator != other.listSeparator && (this.listSeparator == null || !this.listSeparator.equals(other.listSeparator))) {
            return false;
        }
        if (this.reference != other.reference) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.type != null ? this.type.hashCode() : 0);
        hash = 79 * hash + (this.element != null ? this.element.hashCode() : 0);
        hash = 79 * hash + (this.listSeparator != null ? this.listSeparator.hashCode() : 0);
        hash = 79 * hash + (this.reference ? 1 : 0);
        return hash;
    }

}
