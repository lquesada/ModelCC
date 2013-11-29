/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language.memberconstraints;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.modelcc.*;

import org.modelcc.csm.language.MemberConstraint;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.ElementMember;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.types.BooleanModel;
import org.modelcc.types.QuotedStringModel;
import org.modelcc.types.StringModel;

/**
 * Member Constraint class.
 * @author elezeta
 * @serial
 */
@Prefix("(?i)prefix")
public class PrefixMemberConstraint extends MemberConstraint implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Prefix("=")
    @Multiplicity(minimum=0)
    @Separator(",")
    private List<QuotedStringModel> prefixes;
 
	@Override
 	public void apply(Model m, ModelElement me,ElementMember em) {
        List<PatternRecognizer> prefix = new ArrayList<PatternRecognizer>();
        PatternRecognizer pr;
        for (int i = 0;i < prefixes.size();i++) {
        	pr = new RegExpPatternRecognizer(prefixes.get(i).getValue());
        	prefix.add(pr);
        }
    	em.setPrefix(prefix);
	}
    
}
