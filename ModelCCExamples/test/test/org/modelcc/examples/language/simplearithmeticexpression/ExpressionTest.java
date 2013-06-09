/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.simplearithmeticexpression;

import org.modelcc.parser.Parser;
import org.modelcc.parser.fence.adapter.FenceParserFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.Model;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.io.ModelReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelcc.examples.language.simplearithmeticexpression.Expression;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class ExpressionTest {
    
    public ExpressionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
         try {

            ModelReader jmr = new JavaModelReader(Expression.class);
            Model m = jmr.read();
            Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
            se.add(new RegExpPatternRecognizer("[\r \n\t]+"));
            se.add(new RegExpPatternRecognizer("%[^\n]*(\n|$)"));
            parser = FenceParserFactory.create(m,se);

        } catch (Exception ex) {
            Logger.getLogger(ExpressionTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(false);
            return;
        }    
    }
    
    @After
    public void tearDown() {
    }
    
    Parser<Expression> parser;    
    
    private void checkExpression(String exp,double value) {

            Expression result = parser.parse(exp);
            double b = result.eval();
            assertTrue(b-0.1<value);
            assertTrue(b+0.1>value);
    
    }
    
    @Test
    public void ExpressionTest1() {
            checkExpression("(3+2)*2",10);

    }    

    @Test
    public void ExpressionTest2() {
            checkExpression("3+2*2",7);

    }    

    @Test
    public void ExpressionTest3() {
            checkExpression("2*3+2",8);

    }    

    @Test
    public void ExpressionTest4() {
            checkExpression("(2*2)+2",6);

    }    

    @Test
    public void ExpressionTest5() {
            checkExpression("10*5/5",10);

    }    

    @Test
    public void ExpressionTest6() {
            checkExpression("6*2-3*3",3);

    }    

    @Test
    public void ExpressionTest7() {
            checkExpression("1+2+3+4+5+6+7+8+9*10",126);

    }    

    @Test
    public void ExpressionTest8() {
            checkExpression("10*4+5-22+(0.6*52-22)",32.2);

    }    

}
