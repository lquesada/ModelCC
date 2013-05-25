/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.types;

import org.modelcc.types.DoubleModel;
import org.modelcc.types.FloatModel;
import org.modelcc.types.ShortModel;
import org.modelcc.types.LongModel;
import org.modelcc.types.IntegerModel;
import org.modelcc.types.ByteModel;
import org.modelcc.parser.fence.adapter.FenceParserGenerator;
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
            LanguageSpecificationFactory lsf = new LanguageSpecificationFactory();
            Parser p = FenceParserGenerator.create(m);
            return p.parse(text);
        } catch (Exception e) {
            assertTrue(false);
        }
        return null;
    }
    
    @Test
    public void ByteModelTest() {
        assertEquals((new Byte((byte)3)).byteValue(),((ByteModel)testFull(ByteModel.class,"3")).byteValue());
        assertEquals((new Byte((byte)-3)).byteValue(),((ByteModel)testFull(ByteModel.class,"-3")).byteValue());
        assertEquals((new Byte((byte)3)).byteValue(),((ByteModel)testFull(ByteModel.class,"+3")).byteValue());
    }
    
    @Test
    public void IntegerModelTest() {
        assertEquals((new Integer((int)3)).intValue(),((IntegerModel)testFull(IntegerModel.class,"3")).intValue());
        assertEquals((new Integer((int)-3)).intValue(),((IntegerModel)testFull(IntegerModel.class,"-3")).intValue());
        assertEquals((new Integer((int)3)).intValue(),((IntegerModel)testFull(IntegerModel.class,"+3")).intValue());
    }
    
    @Test
    public void ShortModelTest() {
        assertEquals((new Short((short)3)).shortValue(),((ShortModel)testFull(ShortModel.class,"3")).shortValue());
        assertEquals((new Short((short)-3)).shortValue(),((ShortModel)testFull(ShortModel.class,"-3")).shortValue());
        assertEquals((new Short((short)3)).shortValue(),((ShortModel)testFull(ShortModel.class,"+3")).shortValue());
    }
    
    @Test
    public void LongModelTest() {
        assertEquals((new Long((long)3)).longValue(),((LongModel)testFull(LongModel.class,"3")).longValue());
        assertEquals((new Long((long)-3)).longValue(),((LongModel)testFull(LongModel.class,"-3")).longValue());
        assertEquals((new Long((long)3)).longValue(),((LongModel)testFull(LongModel.class,"+3")).longValue());
    }
    
    @Test
    public void FloatModelTest() {
        dassertEquals((new Float((float)3)).floatValue(),((FloatModel)testFull(FloatModel.class,"3.")).floatValue());
        dassertEquals((new Float((float)0.3)).floatValue(),((FloatModel)testFull(FloatModel.class,".3")).floatValue());
        dassertEquals((new Float((float)3)).floatValue(),((FloatModel)testFull(FloatModel.class,"+3.")).floatValue());
        dassertEquals((new Float((float)0.3)).floatValue(),((FloatModel)testFull(FloatModel.class,"+.3")).floatValue());
        dassertEquals((new Float((float)-3)).floatValue(),((FloatModel)testFull(FloatModel.class,"-3.")).floatValue());
        dassertEquals((new Float((float)-0.3)).floatValue(),((FloatModel)testFull(FloatModel.class,"-.3")).floatValue());
        dassertEquals((new Float((float)124.53)).floatValue(),((FloatModel)testFull(FloatModel.class,"1.2453e2")).floatValue());
        dassertEquals((new Float((float)0.012453)).floatValue(),((FloatModel)testFull(FloatModel.class,"1.2453E-2")).floatValue());
    }

    @Test
    public void DoubleModelTest() {
        dassertEquals((new Double((double)3)).doubleValue(),((DoubleModel)testFull(DoubleModel.class,"3.")).doubleValue());
        dassertEquals((new Double((double)0.3)).doubleValue(),((DoubleModel)testFull(DoubleModel.class,".3")).doubleValue());
        dassertEquals((new Double((double)3)).doubleValue(),((DoubleModel)testFull(DoubleModel.class,"+3.")).doubleValue());
        dassertEquals((new Double((double)0.3)).doubleValue(),((DoubleModel)testFull(DoubleModel.class,"+.3")).doubleValue());
        dassertEquals((new Double((double)-3)).doubleValue(),((DoubleModel)testFull(DoubleModel.class,"-3.")).doubleValue());
        dassertEquals((new Double((double)-0.3)).doubleValue(),((DoubleModel)testFull(DoubleModel.class,"-.3")).doubleValue());
        dassertEquals((new Double((double)124.53)).doubleValue(),((DoubleModel)testFull(DoubleModel.class,"1.2453e2")).doubleValue());
        dassertEquals((new Double((double)0.012453)).doubleValue(),((DoubleModel)testFull(DoubleModel.class,"1.2453E-2")).doubleValue());
    }

    private void dassertEquals(double d, double o) {
        assertTrue(d-0.01<o && d+0.01>o);
    }
    
}
