/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.elementconstraints;

import java.io.Serializable;
import org.modelcc.*;

import org.modelcc.csm.language.ElementConstraint;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.types.BooleanModel;
import org.modelcc.types.QuotedStringModel;

/**
 * Pattern value class.
 * @author elezeta
 * @serial
 */
public class RegExpPatternValue extends PatternValue implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private QuotedStringModel regExp;
    
    @Override
    public PatternRecognizer getPattern() {
    	return new RegExpPatternRecognizer(regExp.getValue());
    }
    
}
