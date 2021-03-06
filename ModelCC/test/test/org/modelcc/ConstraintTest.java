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
public class ConstraintTest {


    private Parser genParser(Class a) {
        Parser parser = null;
        try {
            ModelReader jmr = new JavaModelReader(a);
            Model m = jmr.read();
            Set<PatternRecognizer> ignore = new HashSet<PatternRecognizer>();
            parser = ParserFactory.create(m,ignore);
        } catch (Exception ex) {
        	assertTrue(false);
        } 
        return parser;
    }
    
    @Test
    public void ConstraintTest1() throws ParserException {
    	assertEquals(1,genParser(test.languages.constraints.Test1.class).parseAll("0").size());
    	try {
    		genParser(test.languages.constraints.Test1.class).parseAll("10");
    		assertTrue(false);
    	} catch (ParserException e) {
    	}
    }

    @Test
    public void ConstraintTest2() throws ParserException {
    	try {
    		genParser(test.languages.constraints.Test2.class).parseAll("0").size();
    		assertTrue(false);
    	} catch (ParserException e) {
    	}
    	try {
    		genParser(test.languages.constraints.Test2.class).parseAll("10").size();
    		assertTrue(false);
    	} catch (ParserException e) {
    	}
    	assertEquals(1,genParser(test.languages.constraints.Test2.class).parseAll("9").size());
    }

    @Test
    public void ConstraintTest3() throws ParserException {
    	assertEquals(1,genParser(test.languages.constraints.Test3.class).parseAll("5").size());
    }
        
}




