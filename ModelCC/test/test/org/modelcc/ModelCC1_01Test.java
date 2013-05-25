/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc;

import test.languages.extra.ParenthesisPlus;
import org.modelcc.lexer.Lexer;
import java.util.Set;
import java.util.HashSet;
import org.modelcc.io.ModelReader;
import org.modelcc.parser.Parser;
import org.modelcc.parser.fence.adapter.FenceParserGenerator;
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
import org.modelcc.lexer.lamb.adapter.LambLexerGenerator;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import static org.junit.Assert.*;

/**
 *
 *
 * @author elezeta
 */
public class ModelCC1_01Test {

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

            Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
            se.add(new RegExpPatternRecognizer(" "));
            
            Parser<Expression> parser = FenceParserGenerator.create(m,se);

            Expression result = parser.parse("3+(2+5)");
            
            if (result == null)
                assertFalse(true);

            checkExpression(10,result.eval());

        } catch (Exception ex) {
            Logger.getLogger(ModelCC1_01Test.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }
    

    @Test
    public void ModelCCTest2() {

        try {

            ModelReader jmr = new JavaModelReader(Expression.class);

            Model m = jmr.read();

            Lexer lexer = LambLexerGenerator.create(m);
            Parser<Expression> parser = FenceParserGenerator.create(m,lexer);

            Expression result = parser.parse("3+(2+5)");
            if (result == null)
                assertFalse(true);

            checkExpression(10,result.eval());

        } catch (Exception ex) {
            Logger.getLogger(ModelCC1_01Test.class.getName()).log(Level.SEVERE, null, ex);
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

            Lexer lexer = LambLexerGenerator.create(m,mskip);
            Parser<Expression> parser = FenceParserGenerator.create(m,lexer);
            
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
            Lexer lexer = LambLexerGenerator.create(m,ignore);
            Parser<Expression> parser = FenceParserGenerator.create(m,lexer);
            
            Expression result = parser.parse("3+((+2+5)");

            checkExpression(10,result.eval());

        } catch (Exception ex) {
            Logger.getLogger(ModelCC1_00Test.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }
        
}




