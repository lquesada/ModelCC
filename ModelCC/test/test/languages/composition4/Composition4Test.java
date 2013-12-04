/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.composition4;

import static org.junit.Assert.*;
import static org.modelcc.test.ModelAssert.*;

import java.util.Collection;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserException;
import org.modelcc.parser.ParserFactory;
import org.modelcc.parser.fence.Symbol;

public class Composition4Test {

	static Parser<Composition4Amb> parserAmb;
	static Parser<Composition4Eager> parserEager;
	static Parser<Composition4Lazy> parserLazy;

	public static Parser generateParser(Class cl) {
		Model model;
		Parser parser = null;
		try {
	        model = JavaModelReader.read(cl);
	        parser = ParserFactory.create(model,ParserFactory.WHITESPACE);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		return parser;
	}

	private void assertValid(Parser parser, String string) {
		Collection ret = null;
    	try {
    		ret = parser.parseAll(string);
    	} catch (Exception e) {
    		e.printStackTrace();
        	assertFalse(true);
    	}
	
	}

	private void assertInvalid(Parser parser, String string) {
    	try {
    		parser.parseAll(string);
    	} catch (Exception e) {
    		return;
    	}
    	assertFalse(true);
	}
	
	@BeforeClass
	public static void before() {
		parserAmb = generateParser(Composition4Amb.class);
		parserEager = generateParser(Composition4Eager.class);
		parserLazy = generateParser(Composition4Lazy.class);
	}
	
	@Test
	public void test1() {
		assertInterpretations(2, parserAmb, "ab");
	}

	@Test
	public void test2() {
		assertInterpretations(1, parserEager, "ab");
		Composition4Eager val;
		try {
			val = parserEager.parse("ab");
			assertEquals(1,val.rules.length);
		} catch (ParserException e) {
			assertTrue(false);
		}
	}

	@Test
	public void test3() {
		assertInterpretations(1, parserLazy, "ab");
		Composition4Lazy val;
		try {
			val = parserLazy.parse("ab");
			assertEquals(2,val.rules.length);
		} catch (ParserException e) {
			assertTrue(false);
		}
	}

}
