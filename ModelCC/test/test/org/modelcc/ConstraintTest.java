/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc;


import java.util.Set;
import java.util.HashSet;
import org.modelcc.io.ModelReader;
import org.modelcc.parser.Parser;
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
    public void ConstraintTest1() {
    	assertEquals(1,genParser(test.languages.constraints.Test1.class).parseAll("0").size());
    	assertEquals(0,genParser(test.languages.constraints.Test1.class).parseAll("10").size());
    }

    @Test
    public void ConstraintTest2() {
    	assertEquals(0,genParser(test.languages.constraints.Test2.class).parseAll("0").size());
    	assertEquals(0,genParser(test.languages.constraints.Test2.class).parseAll("10").size());
    	assertEquals(1,genParser(test.languages.constraints.Test2.class).parseAll("9").size());
    }

    @Test
    public void ConstraintTest3() {
    	assertEquals(1,genParser(test.languages.constraints.Test3.class).parseAll("5").size());
    }
        
}




