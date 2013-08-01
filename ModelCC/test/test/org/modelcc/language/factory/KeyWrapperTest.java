/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.language.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.language.factory.KeyWrapper;
import org.modelcc.language.factory.ObjectWrapper;
import org.modelcc.metamodel.Model;
import test.languages.keys.IntData;
import test.languages.keys.CharData;
import test.languages.keys.ComplexKey;
import test.languages.keys.Keys1;
import test.languages.keys.Keys2;
/**
 *
 * @author elezeta
 */
public class KeyWrapperTest {
    
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

    public IntData getIntData(int val,Model m,Map<Object,ObjectWrapper> map,boolean hashCheck) {
        IntData il;
        il = new IntData();
        il.value = val;
        int hash = String.valueOf(val).hashCode();
        if (!hashCheck)
            hash = 0;
        map.put(il,new ObjectWrapper(il,m,hash,val));
        return il;
    }
    
    public CharData getCharData(char val,Model m,Map<Object,ObjectWrapper> map,boolean hashCheck) {
        CharData il;
        il = new CharData();
        il.value = val;
        int hash = String.valueOf(val).hashCode();
        if (!hashCheck)
            hash = 0;
        map.put(il,new ObjectWrapper(il,m,hash,val));
        return il;
    }
    
    @Test
    public void keyWrapperTest1() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(Keys1.class);
        } catch (Exception ex) {
            Logger.getLogger(KeyWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,true);
        IntData il2 = getIntData(2,m,map,true);
        IntData il3 = getIntData(3,m,map,true);
        getIntData(4,m,map,true);
        getIntData(1,m,map,true);
        getIntData(2,m,map,true);
        IntData il3b = getIntData(3,m,map,true);
        getIntData(4,m,map,true);
        CharData a1 = getCharData('1',m,map,true);
        CharData a2 = getCharData('2',m,map,true);
        getCharData('3',m,map,true);
        getCharData('4',m,map,true);
        CharData a1b = getCharData('1',m,map,true);
        getCharData('2',m,map,true);
        getCharData('3',m,map,true);
        getCharData('4',m,map,true);

        Keys1 k1 = new Keys1(a1,il1);
        Keys1 k2 = new Keys1(a1,il2);
        Keys1 k3 = new Keys1(a1b,il3);
        Keys1 k4 = new Keys1(a2,il3b);
        
        KeyWrapper kw1 = KeyWrapper.getKeyWrapper(k1, m,map);
        KeyWrapper kw2 = KeyWrapper.getKeyWrapper(k2, m, map);
        KeyWrapper kw3 = KeyWrapper.getKeyWrapper(k3, m, map);
        KeyWrapper kw4 = KeyWrapper.getKeyWrapper(k4, m, map);
        
        assertEquals(kw1.hashCode(),kw2.hashCode());
        assertEquals(kw1,kw2);
        assertEquals(kw1.hashCode(),kw3.hashCode());
        assertEquals(kw1,kw3);
        assertFalse(kw1.equals(kw4));
        assertFalse(kw1.hashCode()==kw4.hashCode());
      }
        
    @Test
    public void keyWrapperTestEquals1() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(Keys1.class);
        } catch (Exception ex) {
            Logger.getLogger(KeyWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,false);
        IntData il2 = getIntData(2,m,map,false);
        IntData il3 = getIntData(3,m,map,false);
        getIntData(4,m,map,false);
        getIntData(1,m,map,false);
        getIntData(2,m,map,false);
        IntData il3b = getIntData(3,m,map,false);
        getIntData(4,m,map,false);
        CharData a1 = getCharData('1',m,map,false);
        CharData a2 = getCharData('2',m,map,false);
        getCharData('3',m,map,false);
        getCharData('4',m,map,false);
        CharData a1b = getCharData('1',m,map,false);
        getCharData('2',m,map,false);
        getCharData('3',m,map,false);
        getCharData('4',m,map,false);

        Keys1 k1 = new Keys1(a1,il1);
        Keys1 k2 = new Keys1(a1,il2);
        Keys1 k3 = new Keys1(a1b,il3);
        Keys1 k4 = new Keys1(a2,il3b);
        
        KeyWrapper kw1 = KeyWrapper.getKeyWrapper(k1, m,map);
        KeyWrapper kw2 = KeyWrapper.getKeyWrapper(k2, m, map);
        KeyWrapper kw3 = KeyWrapper.getKeyWrapper(k3, m, map);
        KeyWrapper kw4 = KeyWrapper.getKeyWrapper(k4, m, map);
        
        assertEquals(kw1,kw2);
        assertEquals(kw1,kw3);
        assertFalse(kw1.equals(kw4));
      }
    
    @Test
    public void keyWrapperTest2() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(Keys2.class);
        } catch (Exception ex) {
            Logger.getLogger(KeyWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,true);
        IntData il2 = getIntData(2,m,map,true);
        IntData il3 = getIntData(3,m,map,true);
        IntData il4 = getIntData(4,m,map,true);
        IntData il1b = getIntData(1,m,map,true);
        IntData il2b = getIntData(2,m,map,true);
        getIntData(3,m,map,true);
        getIntData(4,m,map,true);
        CharData a1 = getCharData('1',m,map,true);
        getCharData('2',m,map,true);
        CharData a3 = getCharData('3',m,map,true);
        getCharData('4',m,map,true);
        getCharData('1',m,map,true);
        getCharData('2',m,map,true);
        getCharData('3',m,map,true);
        getCharData('4',m,map,true);

        IntData[] data1 = new IntData[4];
        IntData[] data2 = new IntData[4];
        IntData[] data3 = new IntData[4];
        IntData[] data4 = new IntData[3];
        data1[0] = il1;
        data1[1] = il2;
        data1[2] = il1;
        data1[3] = il3;
        data2[0] = il1b;
        data2[1] = il2b;
        data2[2] = il1;
        data2[3] = il3;
        data3[0] = il1;
        data3[1] = il2;
        data3[2] = il1;
        data3[3] = il2;
        data4[0] = il1;
        data4[1] = il2;
        data4[2] = il1;
        
        ComplexKey ck1 = new ComplexKey(data1);
        ComplexKey ck2 = new ComplexKey(data2);
        ComplexKey ck3 = new ComplexKey(data3);
        ComplexKey ck4 = new ComplexKey(data4);
        
        Keys2 k1 = new Keys2(ck1,il1,a1);
        Keys2 k2 = new Keys2(ck2,il1b,a3);
        Keys2 k3 = new Keys2(ck3,il1,a1);
        Keys2 k4 = new Keys2(ck1,il2,a1);
        Keys2 k5 = new Keys2(ck4,il1,a1);
        Keys2 k6 = new Keys2(ck4,il4,a1);
        
        KeyWrapper kw1 = KeyWrapper.getKeyWrapper(k1, m,map);
        KeyWrapper kw2 = KeyWrapper.getKeyWrapper(k2, m, map);
        KeyWrapper kw3 = KeyWrapper.getKeyWrapper(k3, m, map);
        KeyWrapper kw4 = KeyWrapper.getKeyWrapper(k4, m, map);
        KeyWrapper kw5 = KeyWrapper.getKeyWrapper(k5, m,map);
        KeyWrapper kw6 = KeyWrapper.getKeyWrapper(k6, m, map);
        
        assertEquals(kw1.hashCode(),kw2.hashCode());
        assertEquals(kw1,kw2);
        assertFalse(kw1.equals(kw3));
        assertFalse(kw1.hashCode()==kw3.hashCode());
        assertFalse(kw1.equals(kw4));
        assertFalse(kw1.hashCode()==kw4.hashCode());
        assertFalse(kw1.equals(kw5));
        assertFalse(kw1.hashCode()==kw5.hashCode());
        assertFalse(kw1.equals(kw6));
        assertFalse(kw1.hashCode()==kw6.hashCode());
      }
        
    @Test
    public void keyWrapperTestEquals2() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(Keys2.class);
        } catch (Exception ex) {
            Logger.getLogger(KeyWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,false);
        IntData il2 = getIntData(2,m,map,false);
        IntData il3 = getIntData(3,m,map,false);
        IntData il4 = getIntData(4,m,map,false);
        IntData il1b = getIntData(1,m,map,false);
        IntData il2b = getIntData(2,m,map,false);
        getIntData(3,m,map,false);
        getIntData(4,m,map,false);
        CharData a1 = getCharData('1',m,map,false);
        getCharData('2',m,map,false);
        CharData a3 = getCharData('3',m,map,false);
        getCharData('4',m,map,false);
        getCharData('1',m,map,false);
        getCharData('2',m,map,false);
        getCharData('3',m,map,false);
        getCharData('4',m,map,false);

        IntData[] data1 = new IntData[4];
        IntData[] data2 = new IntData[4];
        IntData[] data3 = new IntData[4];
        IntData[] data4 = new IntData[3];
        data1[0] = il1;
        data1[1] = il2;
        data1[2] = il1;
        data1[3] = il3;
        data2[0] = il1b;
        data2[1] = il2b;
        data2[2] = il1;
        data2[3] = il3;
        data3[0] = il1;
        data3[1] = il2;
        data3[2] = il1;
        data3[3] = il2;
        data4[0] = il1;
        data4[1] = il2;
        data4[2] = il1;
        
        ComplexKey ck1 = new ComplexKey(data1);
        ComplexKey ck2 = new ComplexKey(data2);
        ComplexKey ck3 = new ComplexKey(data3);
        ComplexKey ck4 = new ComplexKey(data4);
        
        Keys2 k1 = new Keys2(ck1,il1,a1);
        Keys2 k2 = new Keys2(ck2,il1b,a3);
        Keys2 k3 = new Keys2(ck3,il1,a1);
        Keys2 k4 = new Keys2(ck1,il2,a1);
        Keys2 k5 = new Keys2(ck4,il1,a1);
        Keys2 k6 = new Keys2(ck4,il4,a1);
        
        KeyWrapper kw1 = KeyWrapper.getKeyWrapper(k1, m,map);
        KeyWrapper kw2 = KeyWrapper.getKeyWrapper(k2, m, map);
        KeyWrapper kw3 = KeyWrapper.getKeyWrapper(k3, m, map);
        KeyWrapper kw4 = KeyWrapper.getKeyWrapper(k4, m, map);
        KeyWrapper kw5 = KeyWrapper.getKeyWrapper(k5, m,map);
        KeyWrapper kw6 = KeyWrapper.getKeyWrapper(k6, m, map);
        
        assertEquals(kw1,kw2);
        assertFalse(kw1.equals(kw3));
        assertFalse(kw1.equals(kw4));
        assertFalse(kw1.equals(kw5));
        assertFalse(kw1.equals(kw6));
      }
}
