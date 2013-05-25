/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.types;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.modelcc.types.ScientificNotationParser;

/**
 *
 * @author elezeta
 */
public class ScientificNotationParserTest {
    
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

    @Test
    public void RealParseTest() {
        dassertEquals(10.,ScientificNotationParser.parseReal("10"));
        dassertEquals(-10.,ScientificNotationParser.parseReal("-10"));
        dassertEquals(1000.,ScientificNotationParser.parseReal("10e2"));
        dassertEquals(-1000.,ScientificNotationParser.parseReal("-10e2"));
        dassertEquals(37.,ScientificNotationParser.parseReal("+37E+0"));
        dassertEquals(37.,ScientificNotationParser.parseReal("+37e-0"));
        dassertEquals(38.,ScientificNotationParser.parseReal("+380e-1"));
        dassertEquals(40.,ScientificNotationParser.parseReal("4e+1"));
        dassertEquals(46000.,ScientificNotationParser.parseReal("460E2"));
        dassertEquals(0.3,ScientificNotationParser.parseReal(".3"));
        dassertEquals(3,ScientificNotationParser.parseReal(".3e1"));
        dassertEquals(3,ScientificNotationParser.parseReal("3.e0"));

        dassertEquals(137.42,ScientificNotationParser.parseReal("+1.3742e2"));
        dassertEquals(-137.42,ScientificNotationParser.parseReal("-1.3742E+2"));
        dassertEquals(-0.0013742,ScientificNotationParser.parseReal("-1.3742e-3"));
    }

    private void dassertEquals(double d, double o) {
        assertTrue(d-0.01<o && d+0.01>o);
    }
}
