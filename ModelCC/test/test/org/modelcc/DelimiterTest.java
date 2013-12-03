/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc;


import java.util.Set;
import java.util.HashSet;
import org.modelcc.io.ModelReader;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserException;
import org.modelcc.parser.ParserFactory;
import org.modelcc.metamodel.Model;
import org.modelcc.io.java.JavaModelReader;
import org.junit.Test;
import org.modelcc.lexer.recognizer.PatternRecognizer;

import static org.junit.Assert.*;

/**
 *
 *
 * @author elezeta
 */
public class DelimiterTest {


    private Parser genParser(Class a) {
        Parser parser = null;
        try {
            ModelReader jmr = new JavaModelReader(a);
            Model m = jmr.read();
            Set<PatternRecognizer> ignore = new HashSet<PatternRecognizer>();
            parser = ParserFactory.create(m,ignore);
        } catch (Exception ex) {
        	ex.printStackTrace();
        	assertTrue(false);
        } 
        return parser;
    }
    
    
    @Test
    public void PrefixTest1() throws ParserException {
    	assertEquals(1,genParser(test.languages.delimiters.Test1.class).parseAll("a0").size());
    	assertEquals(1,genParser(test.languages.delimiters.Test1.class).parseAll("A0").size());
    	try {
    		genParser(test.languages.delimiters.Test1.class).parseAll("0").size();
    		assertTrue(false);
    	} catch (ParserException e) {
    	}
    	try {
	    	genParser(test.languages.delimiters.Test1.class).parseAll("a").size();
			assertTrue(false);
		} catch (ParserException e) {
		}
    }

    @Test
    public void PrefixTest2() throws ParserException {
    	assertEquals(1,genParser(test.languages.delimiters.Test2.class).parseAll("a42").size());
    	assertEquals(1,genParser(test.languages.delimiters.Test2.class).parseAll("42").size());
    	try {
	    	genParser(test.languages.delimiters.Test2.class).parseAll("aa42").size();
			assertTrue(false);
		} catch (ParserException e) {
		}
    	try {
    		genParser(test.languages.delimiters.Test2.class).parseAll("a").size();
			assertTrue(false);
		} catch (ParserException e) {
		}
    }

    @Test
    public void PrefixTest3() throws ParserException {
    	assertEquals(1,genParser(test.languages.delimiters.Test3.class).parseAll("ab42").size());
    	try {
    		genParser(test.languages.delimiters.Test3.class).parseAll("42").size();
			assertTrue(false);
		} catch (ParserException e) {
		}
    }

    @Test
    public void PrefixTest4() throws ParserException {
    	assertEquals(1,genParser(test.languages.delimiters.Test4.class).parseAll("42").size());
    	try {
    		genParser(test.languages.delimiters.Test4.class).parseAll("a").size();
			assertTrue(false);
		} catch (ParserException e) {
		}
    }

    @Test
    public void SeparatorTest1() throws ParserException {
    	assertEquals(1,genParser(test.languages.delimiters.Test5.class).parseAll("xxxx").size());
    	try {
    		genParser(test.languages.delimiters.Test5.class).parseAll("xxxxb").size();
			assertTrue(false);
		} catch (ParserException e) {
		}
    	try {
    		genParser(test.languages.delimiters.Test5.class).parseAll("xx xx").size();
			assertTrue(false);
		} catch (ParserException e) {
		}
    }

    @Test
    public void SeparatorTest2() throws ParserException {
    	assertEquals(1,genParser(test.languages.delimiters.Test6.class).parseAll("xabxabx").size());
    	try {
    		genParser(test.languages.delimiters.Test6.class).parseAll("xabxabxa").size();
			assertTrue(false);
		} catch (ParserException e) {
		}
    	try {
    		genParser(test.languages.delimiters.Test6.class).parseAll("xabxabxab").size();
			assertTrue(false);
		} catch (ParserException e) {
		}
    }
}




