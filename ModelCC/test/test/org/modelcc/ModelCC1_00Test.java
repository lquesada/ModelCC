/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc;

import org.modelcc.lexer.recognizer.PatternRecognizer;
import java.util.Set;
import java.util.HashSet;
import org.modelcc.io.ModelReader;
import org.modelcc.parser.Parser;
import org.modelcc.parser.fence.adapter.FenceParserFactory;
import org.modelcc.metamodel.Model;
import test.languages.arithmeticcalculator.Expression;
import org.modelcc.io.java.JavaModelReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import test.languages.extra.ParenthesisPlus;
import static org.junit.Assert.*;

/**
 *
 *
 * @author elezeta
 */
public class ModelCC1_00Test {

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }


    void checkExpression(double value,double b) {
        assertTrue(b-0.1<value);
        assertTrue(b+0.1>value);
    }

    @Test
    public void ModelCCTest1() {

        try {

            ModelReader jmr = new JavaModelReader(Expression.class);

            Model m = jmr.read();

            Parser<Expression> parser = FenceParserFactory.create(m);

            Expression result = parser.parse("3+(2+5)");

            checkExpression(10,result.eval());

        } catch (Exception ex) {
            Logger.getLogger(ModelCC1_00Test.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }

    @Test
    public void ModelCCTest2() {

        try {

            Model m = JavaModelReader.read(Expression.class);

            Parser<Expression> parser = FenceParserFactory.create(m);

            Expression result = parser.parse("3+(2+5)");

            checkExpression(10,result.eval());

        } catch (Exception ex) {
            Logger.getLogger(ModelCC1_00Test.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }
    
    @Test
    public void ModelCCTest3() {

        try {

            ModelReader jmr = new JavaModelReader(Expression.class);

            Model m = jmr.read();

            JavaModelReader jmrskip = new JavaModelReader(ParenthesisPlus.class);

            Model mskip = jmrskip.read();

            Parser<Expression> parser = FenceParserFactory.create(m,mskip);

            Expression result = parser.parse("3+((+2+5)");

            checkExpression(10,result.eval());

        } catch (Exception ex) {
            Logger.getLogger(ModelCC1_00Test.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }

    @Test
    public void ModelCCTest4() {

        try {

            ModelReader jmr = new JavaModelReader(Expression.class);

            Model m = jmr.read();

            Set<PatternRecognizer> ignore = new HashSet<PatternRecognizer>();
            ignore.add(new RegExpPatternRecognizer("\\(\\+"));
            Parser<Expression> parser = FenceParserFactory.create(m,ignore);

            Expression result = parser.parse("3+((+2+5)");

            checkExpression(10,result.eval());

        } catch (Exception ex) {
            Logger.getLogger(ModelCC1_00Test.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }    
}




