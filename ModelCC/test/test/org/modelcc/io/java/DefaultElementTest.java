/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.io.java;



import java.util.HashSet;
import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.*;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import test.org.modelcc.ModelCC2_03Test;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class DefaultElementTest {

    @Test
    public void DefaultElementTest1() {

        try {

            ModelReader jmr = new JavaModelReader(test.languages.emptymatchers.basics.StartPoint.class);

            Model m = jmr.read();

            Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
            se.add(new RegExpPatternRecognizer(" "));
            
            Parser<test.languages.emptymatchers.basics.StartPoint> parser = ParserFactory.create(m,se);

            test.languages.emptymatchers.basics.StartPoint result = parser.parse("ab");
            
            if (result == null)
                assertFalse(true);

            assertNotNull(result);
            assertNotNull(result.content);
            assertNotNull(((test.languages.emptymatchers.basics.ContentBasics)result.content).val);
            assertEquals("",((test.languages.emptymatchers.basics.ContentBasics)result.content).val);


        } catch (Exception ex) {
            Logger.getLogger(ModelCC2_03Test.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }
    

    @Test
    public void DefaultElementTest2() {

        try {

            ModelReader jmr = new JavaModelReader(test.languages.emptymatchers.choices.StartPoint.class);

            Model m = jmr.read();

            Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
            se.add(new RegExpPatternRecognizer(" "));
            
            Parser<test.languages.emptymatchers.choices.StartPoint> parser = ParserFactory.create(m,se);

            test.languages.emptymatchers.choices.StartPoint result = parser.parse("ab");
            
            if (result == null)
                assertFalse(true);

            assertNotNull(result);
            assertNotNull(result.content);
            assertNotNull(((test.languages.emptymatchers.choices.ContentChoices2)result.content).val);
            assertEquals("",((test.languages.emptymatchers.choices.ContentChoices2)result.content).val);


        } catch (Exception ex) {
            Logger.getLogger(ModelCC2_03Test.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }

    @Test
    public void DefaultElementTest3() {

        try {

            ModelReader jmr = new JavaModelReader(test.languages.emptymatchers.complex.StartPoint.class);

            Model m = jmr.read();

            Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
            se.add(new RegExpPatternRecognizer(" "));
            
            Parser<test.languages.emptymatchers.complex.StartPoint> parser = ParserFactory.create(m,se);

            test.languages.emptymatchers.complex.StartPoint result = parser.parse("ab");
            
            if (result == null)
                assertFalse(true);

            assertNotNull(result);
            assertNotNull(result.content);
            assertNull(((test.languages.emptymatchers.complex.ContentComplex)result.content).something);
            assertNotNull(((test.languages.emptymatchers.complex.ContentComplex)result.content).empty);


        } catch (Exception ex) {
            Logger.getLogger(ModelCC2_03Test.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }
    
}