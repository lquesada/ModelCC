/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.composition4;

import static org.junit.Assert.*;

import java.util.Collection;

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
	
	private void assertInterpretations(int n,Parser parser, String string) {
		Collection ret = null;
    	try {
    		ret = parser.parseAll(string);
    		for (Object o : ret) {
    			System.out.println("Printing object "+o);
    			printTree((Symbol)parser.getParsingMetadata(o).get("symbol"));
    		}
    		assertEquals(n,ret.size());
    	} catch (Exception e) {
    		assertEquals(n,0);
    		e.printStackTrace();
        	assertFalse(true);
    	}
	
	}
	private void printTree(Symbol symbol) {
		printTree(symbol,0);
	}

	private void printTree(Symbol symbol,int depth) {
		char tab[] = new char[depth*2];
		for (int i = 0;i < depth*2;i++)
			tab[i] = ' ';
		String tabs = new String(tab);
		System.out.println(tabs+symbol.getType());
		for (Symbol s : symbol.getContents()) {
			printTree(s,depth+1);
		}
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
