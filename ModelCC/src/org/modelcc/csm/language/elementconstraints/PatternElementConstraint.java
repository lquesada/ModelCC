/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.elementconstraints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.modelcc.*;

import org.modelcc.csm.language.ElementConstraint;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.BasicModelElement;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.ElementMember;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.types.BooleanModel;
import org.modelcc.types.QuotedStringModel;

/**
 * Element Constraint class.
 * @author elezeta
 * @serial
 */
@Prefix("(?i)pattern")
public class PatternElementConstraint extends ElementConstraint implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Prefix("=")
    private PatternValue pattern;
 
	@Override
 	public void apply(Model m, ModelElement me) {
		if (!BasicModelElement.class.isAssignableFrom(me.getClass())) {
			//TODO error not valid.
		}
		else {
			BasicModelElement bme = (BasicModelElement)me;
			PatternRecognizer pr = pattern.getPattern();
			if (pr != null)
				bme.setPatternRecognizer(pr);
		}
	}
    
}
