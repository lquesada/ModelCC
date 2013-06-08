/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.metamodel;

import java.util.List;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.AssociativityType;

/**
 * Select element.
 * @author elezeta
 * @serial
 */
public final class ChoiceModelElement extends ModelElement {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Constructor.
     * @param elementClass the element class
     * @param associativity the associativity
     * @param prefix the prefix
     * @param suffix the suffix
     * @param separator the default separator
     * @param setupMethod the setup method
     * @param constraintMethods the constraint checking methods
     * @param hasAnyAssociativity has any associativity 
     */
    public ChoiceModelElement(Class elementClass,AssociativityType associativity,List<PatternRecognizer> prefix,List<PatternRecognizer> suffix,List<PatternRecognizer> separator,String setupMethod,List<String> constraintMethods,boolean hasAnyAssociativity) {
        super(elementClass,associativity,prefix,suffix,separator,setupMethod,constraintMethods,hasAnyAssociativity);
    }

    
}
