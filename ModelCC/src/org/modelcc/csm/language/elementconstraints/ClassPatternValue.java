/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.elementconstraints;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelcc.*;

import org.modelcc.csm.CSM;
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
    	} catch (ClassNotFoundException e) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Pattern matcher class {0} not found.",new Object[]{matcher.getName()});
        	return null;
    	} catch (IllegalArgumentException e) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Pattern matcher class {0} could not be instanced (illegal argument).",new Object[]{matcher.getName()});
        	return null;
		} catch (SecurityException e) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Pattern matcher class {0} could not be instanced (security).",new Object[]{matcher.getName()});
        	return null;
		} catch (InstantiationException e) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Pattern matcher class {0} could not be instanced (instantiation).",new Object[]{matcher.getName()});
        	return null;
		} catch (IllegalAccessException e) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Pattern matcher class {0} could not be instanced (illegal access).",new Object[]{matcher.getName()});
        	return null;
		} catch (InvocationTargetException e) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Pattern matcher class {0} could not be instanced (invocation target).",new Object[]{matcher.getName()});
        	return null;
		} catch (NoSuchMethodException e) {
			Logger.getLogger(CSM.class.getName()).log(Level.SEVERE,"Pattern matcher class {0} could not be instanced (no such method).",new Object[]{matcher.getName()});
        	return null;
		}
    }
    
}
