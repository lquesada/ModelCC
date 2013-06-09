/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.examples.language.canvasdraw;

import org.modelcc.examples.language.canvasdraw.CanvasDraw;
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
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class CanvasDrawTest {
    
    public CanvasDrawTest() {
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

            ModelReader jmr = new JavaModelReader(CanvasDraw.class);
            Model m = jmr.read();
            Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
            se.add(new RegExpPatternRecognizer("[\r \n\t]+"));
            se.add(new RegExpPatternRecognizer("%[^\n]*(\n|$)"));
            parser = FenceParserFactory.create(m,se);

        } catch (Exception ex) {
            Logger.getLogger(CanvasDrawTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }    
    }
    
    @After
    public void tearDown() {
    }
    
    Parser<CanvasDraw> parser;    
    

    @Test
    public void CanvasDrawTest1() {
            assertEquals(1,parser.parseAll("canvas").size());
    }    

    @Test
    public void CanvasDrawTest2() {
            assertEquals(1,parser.parseAll("canvas width 640").size());
    }    

    @Test
    public void CanvasDrawTest3() {
            assertEquals(1,parser.parseAll("canvas line [(0,0),(10,10)]").size());
    }    

    @Test
    public void CanvasDrawTest4() {
            assertEquals(1,parser.parseAll("canvas width 320 line [(0,0),(10,10)]").size());
    }    

    @Test
    public void CanvasDrawTest5() {
            assertEquals(1,parser.parseAll("canvas width 640 height 480 background (180,255,255) line [(10,10),(20,20)] rectangle [(40,100),(60,120)] circle (400,30),400").size());
    }    

    @Test
    public void CanvasDrawTest6() {
            assertEquals(1,parser.parseAll("canvas width 640 height 480 background (0,0,0) color (255,0,0) line [(10,10),(20,20)] rectangle [(40,100),(60,120)] fill color (0,255,0) circle (400,30),400").size());
    }    

    @Test
    public void CanvasDrawTest7() {
            assertEquals(1,parser.parseAll("canvas %test").size());
    }    

}
