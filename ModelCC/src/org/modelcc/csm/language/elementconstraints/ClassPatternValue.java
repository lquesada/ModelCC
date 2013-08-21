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
import org.modelcc.tools.ClassFinder;
import org.modelcc.types.BooleanModel;
import org.modelcc.types.QuotedStringModel;

/**
 * Pattern value class.
 * @author elezeta
 * @serial
 */
@Prefix("\\(")
@Suffix("\\)")
public class ClassPatternValue extends PatternValue implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private ClassName matcher;

    @Optional
    @Prefix(":")
    private QuotedStringModel arg; 
    
    @Override
    public PatternRecognizer getPattern() {
    	try {
    		Class c = ClassFinder.findClass(matcher.getName());
    		if (arg==null)
    			return (PatternRecognizer) c.getConstructor(String.class).newInstance("");
    		else
    			return (PatternRecognizer) c.getConstructor(String.class).newInstance(arg.getValue());
    	} catch (Exception e) {
    		//TODO
        	return null;
    	}
    }
    
}
