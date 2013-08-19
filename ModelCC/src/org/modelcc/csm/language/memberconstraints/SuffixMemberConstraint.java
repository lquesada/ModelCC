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

/**
 * Member Constraint class.
 * @author elezeta
 * @serial
 */
@Prefix("(?i)suffix")
public class SuffixMemberConstraint extends MemberConstraint implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Prefix("=")
    @Minimum(0)
    @Separator(",")
    private List<QuotedStringModel> suffixes;
 
	@Override
 	public void apply(Model m, ModelElement me,ElementMember em) {
        List<PatternRecognizer> suffix = new ArrayList<PatternRecognizer>();
        PatternRecognizer pr;
        for (int i = 0;i < suffixes.size();i++) {
        	pr = new RegExpPatternRecognizer(suffixes.get(i).getValue());
        	suffix.add(pr);
        }
    	em.setSuffix(suffix);
	}
    
}
