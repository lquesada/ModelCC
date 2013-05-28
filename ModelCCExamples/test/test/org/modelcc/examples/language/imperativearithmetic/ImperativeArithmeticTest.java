/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.imperativearithmetic;

import org.modelcc.examples.language.imperativearithmetic.ImperativeArithmetic;
import org.modelcc.parser.Parser;
import org.modelcc.parser.fence.adapter.FenceParserFactory;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.modelcc.metamodel.Model;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.io.ModelReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class ImperativeArithmeticTest {
    
    public ImperativeArithmeticTest() {
    }

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
    
    
    public Parser<ImperativeArithmetic> parserGen() {
        Parser<ImperativeArithmetic> parser = null;    

        try {
            ModelReader jmr = new JavaModelReader(ImperativeArithmetic.class);
            Model m = jmr.read();
            parser = FenceParserFactory.create(m);

        } catch (Exception ex) {
            Logger.getLogger(ImperativeArithmeticTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(false);
        }    
        return parser;
    }
    @Test
    public void InputTest1() {
        Object o = parserGen().parse("");
        assertNotNull(o);
    }    

    @Test
    public void InputTest2() {
        assertEquals("1.0\n",parserGen().parse("sentences output 1.0").run());
    }    

    @Test
    public void InputTest3() {
        assertEquals("",parserGen().parse("variables var a").run());
    }    

    @Test
    public void InputTest4() {
        assertEquals("1.0\n3.0\n8.0\n",parserGen().parse("variables var a var b var c sentences a = 1 b = a+2 c = (a+b)*2 output a output b output c").run());
    }  
    
    @Test
    public void InputTest5() {
        Parser<ImperativeArithmetic> parser = parserGen();
        assertEquals("1.0\n3.0\n8.0\n",parser.parse("variables var a var b var c sentences a = 1 b = a+2 c = (a+b)*2 output a output b output c").run());
        assertEquals("0.0\n",parser.parse("variables var a sentences output a").run());
    }  
    
    @Test
    public void InputTest6() {
        Parser<ImperativeArithmetic> parser = parserGen();
        assertEquals("1.0\n3.0\n8.0\n",parser.parse("variables var a var b var c sentences a = 1 b = a+2 c = (a+b)*2 output a output b output c").run());
        assertNull(parser.parse("sentences output a"));
    } 
}
