/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ProbabilisticParserFactory;

import test.languages.arithmeticcalculator.Expression;

/**
 * Assert class.
 * @author elezeta
 * @serial
 */
public class Assert {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    
    private Assert() { }
    
    public static void assertValid(Model m,String string) {
    	assertValid(m,new HashSet<PatternRecognizer>(),string);
    }

    public static void assertInvalid(Model m,String string) {
    	assertInvalid(m,new HashSet<PatternRecognizer>(),string);
    }

    public static void assertAmbiguityFree(Model m,String string) {
    	assertAmbiguityFree(m,new HashSet<PatternRecognizer>(),string);
    }

    public static void assertInterpretations(int n,Model m,String string) {
    	assertInterpretations(n,m,new HashSet<PatternRecognizer>(),string);
    }

    public static void assertValid(Model m,Set<PatternRecognizer> se,String string) {
    	
    }

    public static void assertInvalid(Model m,Set<PatternRecognizer> se,String string) {
    	
    }

    public static void assertAmbiguityFree(Model m,Set<PatternRecognizer> se,String string) {
    	
    }

    public static void assertInterpretations(int n,Model m,Set<PatternRecognizer> se,String string) {
    	
    }
    
}


/*
Model m = jmr.read();

Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
se.add(new RegExpPatternRecognizer(" "));

Parser<Expression> parser = ProbabilisticParserFactory.create(m,se);

Expression result = parser.parse("3+(2+5)");

if (result == null)
    assertFalse(true);

checkExpression(10,result.eval());
*/