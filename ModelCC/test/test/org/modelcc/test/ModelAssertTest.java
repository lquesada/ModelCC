/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import test.languages.arithmeticcalculator.Expression;

import static org.junit.Assert.*;
import static org.modelcc.test.ModelAssert.*;

/**
 *
 *
 * @author elezeta
 */
public class ModelAssertTest {

    @Test
    public void AssertTest1() {

        try {

            Model model = JavaModelReader.read(Expression.class);
            Parser<Expression> parser = ParserFactory.create(model,ParserFactory.WHITESPACE);

            assertValid(parser,"1+2");
            assertValid(parser,"1+ 2");
            assertInvalid(parser,"a1+2");
            assertAmbiguityFree(parser,"1+2");
            assertInterpretations(1,parser,"1+2+5");

        } catch (Exception ex) {
            assertFalse(true);
            return;
        }


        {
            Model model;
            Parser<Expression> parser;
            try {
				model = JavaModelReader.read(Expression.class);
	            Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
	            se.add(new RegExpPatternRecognizer(" "));
	            parser = ParserFactory.create(model,se);
			} catch (Exception e) {
				assertFalse(true);
				return;
			}
	
	        try {
	            assertInvalid(parser,"1+2");
	            assertFalse(true);
	        } catch (AssertionError ex) {
	        }

	        try {
	            assertValid(parser,"ab");
	            assertFalse(true);
	        } catch (AssertionError ex) {
	        }

	        try {
	            assertInterpretations(2,parser,"1+2+5");
	            assertFalse(true);
	        } catch (AssertionError ex) {
	        }

        }

    }

}

