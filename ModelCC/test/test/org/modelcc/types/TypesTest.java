/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.types;

import org.modelcc.types.DecimalModel;
import org.modelcc.types.IntegerModel;
import org.modelcc.parser.fence.adapter.FenceParserFactory;
import org.modelcc.metamodel.Model;
import org.modelcc.io.java.JavaModelReader;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelcc.language.factory.LanguageSpecificationFactory;
import org.modelcc.parser.Parser;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class TypesTest {
    
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

    public Object testFull(Class c,String text) {
        try {
            Model m;
            JavaModelReader jmr = new JavaModelReader(c);
            m = jmr.read();
            new LanguageSpecificationFactory();
            Parser p = FenceParserFactory.create(m);
            return p.parse(text);
        } catch (Exception e) {
            assertTrue(false);
        }
        return null;
    }
        
    @Test
    public void IntegerModelTest() {
        //assertEquals((new Integer(3)).intValue(),((IntegerModel)testFull(IntegerModel.class,"3")).intValue());
        //assertEquals((new Integer(-3)).intValue(),((IntegerModel)testFull(IntegerModel.class,"-3")).intValue());
        assertEquals((new Integer(3)).intValue(),((IntegerModel)testFull(IntegerModel.class,"+3")).intValue());
    }
    
        @Test
    public void DecimalModelTest() {
        dassertEquals((new Double(3)).doubleValue(),((DecimalModel)testFull(DecimalModel.class,"3.")).doubleValue());
        dassertEquals((new Double(0.3)).doubleValue(),((DecimalModel)testFull(DecimalModel.class,".3")).doubleValue());
        dassertEquals((new Double(3)).doubleValue(),((DecimalModel)testFull(DecimalModel.class,"+3.")).doubleValue());
        dassertEquals((new Double(0.3)).doubleValue(),((DecimalModel)testFull(DecimalModel.class,"+.3")).doubleValue());
        dassertEquals((new Double(-3)).doubleValue(),((DecimalModel)testFull(DecimalModel.class,"-3.")).doubleValue());
        dassertEquals((new Double(-0.3)).doubleValue(),((DecimalModel)testFull(DecimalModel.class,"-.3")).doubleValue());
        dassertEquals((new Double(124.53)).doubleValue(),((DecimalModel)testFull(DecimalModel.class,"1.2453e2")).doubleValue());
        dassertEquals((new Double(0.012453)).doubleValue(),((DecimalModel)testFull(DecimalModel.class,"1.2453E-2")).doubleValue());
    }

    private void dassertEquals(double d, double o) {
        assertTrue(d-0.01<o && d+0.01>o);
    }
    
}
