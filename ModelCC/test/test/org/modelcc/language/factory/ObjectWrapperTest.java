/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.language.factory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import org.modelcc.language.factory.ObjectWrapper;
import org.modelcc.metamodel.Model;
import test.languages.arithmeticcalculator.BinaryOperator;
import test.languages.arithmeticcalculator.Expression;
import test.languages.arithmeticcalculator.binaryoperators.AdditionOperator;
import test.languages.arithmeticcalculator.binaryoperators.DivisionOperator;
import test.languages.arithmeticcalculator.binaryoperators.MultiplicationOperator;
import test.languages.arithmeticcalculator.binaryoperators.SubstractionOperator;
import test.languages.arithmeticcalculator.expressions.BinaryExpression;
import test.languages.arithmeticcalculator.expressions.literals.IntegerLiteral;
import test.languages.containertest.IntData;
import test.languages.containertest.LibArray;
import test.languages.containertest.LibList;
import test.languages.containertest.LibSet;
/**
 *
 * @author elezeta
 */
public class ObjectWrapperTest {
    
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

    public IntegerLiteral getLiteral(int val,Model m,Map<Object,ObjectWrapper> map,boolean hashCheck) {
        IntegerLiteral il;
        il = new IntegerLiteral();
        il.value = val;
        int hash = String.valueOf(il.value).hashCode();
        if (!hashCheck)
            hash = 0;
        map.put(il,new ObjectWrapper(il,m,hash,val));
        return il;
    }
   
    
    public BinaryOperator getBinaryOperator(char opc,Model m,Map<Object,ObjectWrapper> map,boolean hashCheck) {
        BinaryOperator op = null;
        switch (opc) {
            case '+':
                op = new AdditionOperator();
                break;
            case '-':
                op = new SubstractionOperator();
                break;
            case '/':
                op = new DivisionOperator();
                break;
            case '*':
                op = new MultiplicationOperator();
                break;
        }
        int hash = String.valueOf(opc).hashCode();
        if (!hashCheck)
            hash = 0;
        
        map.put(op,new ObjectWrapper(op,m,hash,opc));
        return op;
    }
    
    public BinaryExpression getBinaryExpression(Expression e1,BinaryOperator op,Expression e2,boolean hashCheck) {
        BinaryExpression b;
        b = new BinaryExpression();
        b.e1 = e1;
        b.op = op;
        b.e2 = e2;
        return b;
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
    
    
    @Test
    public void objectWrapperTest1() {
        
        Model m = null;
        try {
            m = JavaModelReader.read(Expression.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();

        IntegerLiteral il1 = getLiteral(1,m,map,true);
        IntegerLiteral il2 = getLiteral(2,m,map,true);
        IntegerLiteral il3 = getLiteral(2,m,map,true);
        
        ObjectWrapper ow1l = ObjectWrapper.getObjectWrapper(il1,m,map);
        ObjectWrapper ow2l = ObjectWrapper.getObjectWrapper(il2,m,map);
        ObjectWrapper ow3l = ObjectWrapper.getObjectWrapper(il3,m,map);
        
        assertEquals(ow2l,ow2l);
        assertEquals(ow3l,ow3l);
        assertEquals(ow2l,ow3l);
        assertEquals(ow2l.hashCode(),ow3l.hashCode());
        assertFalse(ow2l.equals(ow1l));
        assertFalse(ow2l.hashCode()==ow1l.hashCode());
        
    }
    
    
    @Test
    public void objectWrapperTest2() {

        Model m = null;
        try {
            m = JavaModelReader.read(Expression.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        IntegerLiteral il1 = getLiteral(1,m,map,true);
        IntegerLiteral il2 = getLiteral(2,m,map,true);
        IntegerLiteral il3 = getLiteral(1,m,map,true);
        IntegerLiteral il4 = getLiteral(2,m,map,true);
        
        BinaryOperator op1 = getBinaryOperator('+',m,map,true);
        BinaryOperator op2 = getBinaryOperator('+',m,map,true);
        BinaryOperator op3 = getBinaryOperator('-',m,map,true);

        BinaryExpression b1 = getBinaryExpression(il1,op1,il2,true);
        BinaryExpression b2 = getBinaryExpression(il3,op2,il4,true);
        BinaryExpression b3 = getBinaryExpression(il3,op2,il1,true);
        BinaryExpression b4 = getBinaryExpression(il1,op3,il2,true);
        
        ObjectWrapper ow1 = ObjectWrapper.getObjectWrapper(b1,m,map);
        ObjectWrapper ow2 = ObjectWrapper.getObjectWrapper(b2,m,map);
        ObjectWrapper ow3 = ObjectWrapper.getObjectWrapper(b3,m,map);
        ObjectWrapper ow4 = ObjectWrapper.getObjectWrapper(b4,m,map);
        
        assertEquals(ow1.hashCode(),ow2.hashCode());
        assertEquals(ow1,ow2);
        assertFalse(ow3.hashCode()==ow2.hashCode());
        assertFalse(ow3.equals(ow2));
        assertFalse(ow4.hashCode()==ow1.hashCode());
        assertFalse(ow4.equals(ow1));

    }
    
        
    @Test
    public void objectWrapperArrayTest() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(LibArray.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,true);
        IntData il2 = getIntData(2,m,map,true);
        IntData il3 = getIntData(3,m,map,true);
        IntData il4 = getIntData(4,m,map,true);
        IntData il1b = getIntData(1,m,map,true);
        IntData il2b = getIntData(2,m,map,true);
        IntData il3b = getIntData(3,m,map,true);
        IntData il4b = getIntData(4,m,map,true);

        LibArray list1 = new LibArray();
        list1.data = new IntData[4];
        list1.data[0] = il1;
        list1.data[1] = il2;
        list1.data[2] = il3;
        list1.data[3] = il4;
        

        LibArray list2 = new LibArray();
        list2.data = new IntData[4];
        list2.data[0] = il1b;
        list2.data[1] = il2b;
        list2.data[2] = il3b;
        list2.data[3] = il4b;
        

        LibArray list3 = new LibArray();
        list3.data = new IntData[4];
        list3.data[0] = il1b;
        list3.data[1] = il3b;
        list3.data[2] = il2b;
        list3.data[3] = il4b;
        

        ObjectWrapper ow1 = ObjectWrapper.getObjectWrapper(list1,m,map);
        ObjectWrapper ow2 = ObjectWrapper.getObjectWrapper(list2,m,map);
        ObjectWrapper ow3 = ObjectWrapper.getObjectWrapper(list3,m,map);
        
        assertEquals(ow1,ow2);
        assertEquals(ow1.hashCode(),ow2.hashCode());
        assertFalse(ow1.equals(ow3));
        assertFalse(ow1.hashCode()==ow3.hashCode());
      }
    

    @Test
    public void objectWrapperListTest() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(LibList.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,true);
        IntData il2 = getIntData(2,m,map,true);
        IntData il3 = getIntData(3,m,map,true);
        IntData il4 = getIntData(4,m,map,true);
        IntData il1b = getIntData(1,m,map,true);
        IntData il2b = getIntData(2,m,map,true);
        IntData il3b = getIntData(3,m,map,true);
        IntData il4b = getIntData(4,m,map,true);

        LibList list1 = new LibList();
        list1.data = new ArrayList<IntData>();
        list1.data.add(il1);
        list1.data.add(il2);
        list1.data.add(il3);
        list1.data.add(il4);
        

        LibList list2 = new LibList();
        list2.data = new ArrayList<IntData>();
        list2.data.add(il1b);
        list2.data.add(il2b);
        list2.data.add(il3b);
        list2.data.add(il4b);
        

        LibList list3 = new LibList();
        list3.data = new ArrayList<IntData>();
        list3.data.add(il1b);
        list3.data.add(il3b);
        list3.data.add(il2b);
        list3.data.add(il4b);
        

        ObjectWrapper ow1 = ObjectWrapper.getObjectWrapper(list1,m,map);
        ObjectWrapper ow2 = ObjectWrapper.getObjectWrapper(list2,m,map);
        ObjectWrapper ow3 = ObjectWrapper.getObjectWrapper(list3,m,map);
        
        assertEquals(ow1,ow2);
        assertEquals(ow1.hashCode(),ow2.hashCode());
        assertFalse(ow1.equals(ow3));
        assertFalse(ow1.hashCode()==ow3.hashCode());
                
    }
    

    @Test
    public void objectWrapperSetTest() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(LibSet.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,true);
        IntData il2 = getIntData(2,m,map,true);
        IntData il3 = getIntData(3,m,map,true);
        IntData il4 = getIntData(4,m,map,true);
        IntData il1b = getIntData(1,m,map,true);
        IntData il2b = getIntData(2,m,map,true);
        IntData il3b = getIntData(3,m,map,true);
        IntData il4b = getIntData(4,m,map,true);

        LibSet list1 = new LibSet();
        list1.data = new HashSet<IntData>();
        list1.data.add(il1);
        list1.data.add(il2);
        list1.data.add(il3);
        list1.data.add(il4);
        

        LibSet list2 = new LibSet();
        list2.data = new HashSet<IntData>();
        list2.data.add(il1b);
        list2.data.add(il2b);
        list2.data.add(il3b);
        list2.data.add(il4b);
        

        LibSet list3 = new LibSet();
        list3.data = new HashSet<IntData>();
        list3.data.add(il1b);
        list3.data.add(il3b);
        list3.data.add(il2b);
        list3.data.add(il4b);
        
        LibSet list4 = new LibSet();
        list4.data = new HashSet<IntData>();
        list4.data.add(il1b);
        list4.data.add(il3b);
        list4.data.add(il2b);
        

        ObjectWrapper ow1 = ObjectWrapper.getObjectWrapper(list1,m,map);
        ObjectWrapper ow2 = ObjectWrapper.getObjectWrapper(list2,m,map);
        ObjectWrapper ow3 = ObjectWrapper.getObjectWrapper(list3,m,map);
        ObjectWrapper ow4 = ObjectWrapper.getObjectWrapper(list4,m,map);
        
        assertEquals(ow1,ow2);
        assertEquals(ow1.hashCode(),ow2.hashCode());
        assertEquals(ow1,ow3);
        assertEquals(ow1.hashCode(),ow3.hashCode());
        assertFalse(ow1.equals(ow4));
        assertFalse(ow1.hashCode()==ow4.hashCode());
                
    }


    @Test
    public void objectWrapperEqualsTest1() {
        
        Model m = null;
        try {
            m = JavaModelReader.read(Expression.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();

        IntegerLiteral il1 = getLiteral(1,m,map,false);
        IntegerLiteral il2 = getLiteral(2,m,map,false);
        IntegerLiteral il3 = getLiteral(2,m,map,false);
        
        ObjectWrapper ow1l = ObjectWrapper.getObjectWrapper(il1,m,map);
        ObjectWrapper ow2l = ObjectWrapper.getObjectWrapper(il2,m,map);
        ObjectWrapper ow3l = ObjectWrapper.getObjectWrapper(il3,m,map);
        
        assertEquals(ow2l,ow2l);
        assertEquals(ow3l,ow3l);
        assertEquals(ow2l,ow3l);
        assertFalse(ow2l.equals(ow1l));
        
    }
    
    
    @Test
    public void objectWrapperEqualsTest2() {

        Model m = null;
        try {
            m = JavaModelReader.read(Expression.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        IntegerLiteral il1 = getLiteral(1,m,map,false);
        IntegerLiteral il2 = getLiteral(2,m,map,false);
        IntegerLiteral il3 = getLiteral(1,m,map,false);
        IntegerLiteral il4 = getLiteral(2,m,map,false);
        
        BinaryOperator op1 = getBinaryOperator('+',m,map,false);
        BinaryOperator op2 = getBinaryOperator('+',m,map,false);
        BinaryOperator op3 = getBinaryOperator('-',m,map,false);

        BinaryExpression b1 = getBinaryExpression(il1,op1,il2,false);
        BinaryExpression b2 = getBinaryExpression(il3,op2,il4,false);
        BinaryExpression b3 = getBinaryExpression(il3,op2,il1,false);
        BinaryExpression b4 = getBinaryExpression(il1,op3,il2,false);
        
        ObjectWrapper ow1 = ObjectWrapper.getObjectWrapper(b1,m,map);
        ObjectWrapper ow2 = ObjectWrapper.getObjectWrapper(b2,m,map);
        ObjectWrapper ow3 = ObjectWrapper.getObjectWrapper(b3,m,map);
        ObjectWrapper ow4 = ObjectWrapper.getObjectWrapper(b4,m,map);
        
        assertEquals(ow1,ow2);
        assertFalse(ow3.equals(ow2));
        assertFalse(ow4.equals(ow1));

    }
    
        
    @Test
    public void objectWrapperArrayEqualsTest() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(LibArray.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,false);
        IntData il2 = getIntData(2,m,map,false);
        IntData il3 = getIntData(3,m,map,false);
        IntData il4 = getIntData(4,m,map,false);
        IntData il1b = getIntData(1,m,map,false);
        IntData il2b = getIntData(2,m,map,false);
        IntData il3b = getIntData(3,m,map,false);
        IntData il4b = getIntData(4,m,map,false);

        LibArray list1 = new LibArray();
        list1.data = new IntData[4];
        list1.data[0] = il1;
        list1.data[1] = il2;
        list1.data[2] = il3;
        list1.data[3] = il4;
        

        LibArray list2 = new LibArray();
        list2.data = new IntData[4];
        list2.data[0] = il1b;
        list2.data[1] = il2b;
        list2.data[2] = il3b;
        list2.data[3] = il4b;
        

        LibArray list3 = new LibArray();
        list3.data = new IntData[4];
        list3.data[0] = il1b;
        list3.data[1] = il3b;
        list3.data[2] = il2b;
        list3.data[3] = il4b;
        

        ObjectWrapper ow1 = ObjectWrapper.getObjectWrapper(list1,m,map);
        ObjectWrapper ow2 = ObjectWrapper.getObjectWrapper(list2,m,map);
        ObjectWrapper ow3 = ObjectWrapper.getObjectWrapper(list3,m,map);
        
        assertEquals(ow1,ow2);
        assertFalse(ow1.equals(ow3));
      }
    

    @Test
    public void objectWrapperListEqualsTest() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(LibList.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,false);
        IntData il2 = getIntData(2,m,map,false);
        IntData il3 = getIntData(3,m,map,false);
        IntData il4 = getIntData(4,m,map,false);
        IntData il1b = getIntData(1,m,map,false);
        IntData il2b = getIntData(2,m,map,false);
        IntData il3b = getIntData(3,m,map,false);
        IntData il4b = getIntData(4,m,map,false);

        LibList list1 = new LibList();
        list1.data = new ArrayList<IntData>();
        list1.data.add(il1);
        list1.data.add(il2);
        list1.data.add(il3);
        list1.data.add(il4);
        

        LibList list2 = new LibList();
        list2.data = new ArrayList<IntData>();
        list2.data.add(il1b);
        list2.data.add(il2b);
        list2.data.add(il3b);
        list2.data.add(il4b);
        

        LibList list3 = new LibList();
        list3.data = new ArrayList<IntData>();
        list3.data.add(il1b);
        list3.data.add(il3b);
        list3.data.add(il2b);
        list3.data.add(il4b);
        

        ObjectWrapper ow1 = ObjectWrapper.getObjectWrapper(list1,m,map);
        ObjectWrapper ow2 = ObjectWrapper.getObjectWrapper(list2,m,map);
        ObjectWrapper ow3 = ObjectWrapper.getObjectWrapper(list3,m,map);
        
        assertEquals(ow1,ow2);
        assertFalse(ow1.equals(ow3));
                
    }
    

    @Test
    public void objectWrapperSetEqualsTest() {
        Map<Object,ObjectWrapper> map = new HashMap<Object,ObjectWrapper>();
        
        Model m = null;
        try {
            m = JavaModelReader.read(LibSet.class);
        } catch (Exception ex) {
            Logger.getLogger(ObjectWrapperTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }
        IntData il1 = getIntData(1,m,map,false);
        IntData il2 = getIntData(2,m,map,false);
        IntData il3 = getIntData(3,m,map,false);
        IntData il4 = getIntData(4,m,map,false);
        IntData il1b = getIntData(1,m,map,false);
        IntData il2b = getIntData(2,m,map,false);
        IntData il3b = getIntData(3,m,map,false);
        IntData il4b = getIntData(4,m,map,false);

        LibSet list1 = new LibSet();
        list1.data = new HashSet<IntData>();
        list1.data.add(il1);
        list1.data.add(il2);
        list1.data.add(il3);
        list1.data.add(il4);
        

        LibSet list2 = new LibSet();
        list2.data = new HashSet<IntData>();
        list2.data.add(il1b);
        list2.data.add(il2b);
        list2.data.add(il3b);
        list2.data.add(il4b);
        

        LibSet list3 = new LibSet();
        list3.data = new HashSet<IntData>();
        list3.data.add(il1b);
        list3.data.add(il3b);
        list3.data.add(il2b);
        list3.data.add(il4b);
        
        LibSet list4 = new LibSet();
        list4.data = new HashSet<IntData>();
        list4.data.add(il1b);
        list4.data.add(il3b);
        list4.data.add(il2b);
        

        ObjectWrapper ow1 = ObjectWrapper.getObjectWrapper(list1,m,map);
        ObjectWrapper ow2 = ObjectWrapper.getObjectWrapper(list2,m,map);
        ObjectWrapper ow3 = ObjectWrapper.getObjectWrapper(list3,m,map);
        ObjectWrapper ow4 = ObjectWrapper.getObjectWrapper(list4,m,map);
        
        assertEquals(ow1,ow2);
        assertEquals(ow1,ow3);
        assertFalse(ow1.equals(ow4));
                
    }
    
}
