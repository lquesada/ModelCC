/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.lexer.recognizer.regexp;

import org.modelcc.lexer.recognizer.regexp.ShiftingCharSequence;
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
public class ShiftingCharSequenceTest {

    public ShiftingCharSequenceTest() {
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

    @Test
    public void ShiftTest1() {
        ShiftingCharSequence s = new ShiftingCharSequence("hello");

        assertEquals("hello",s.toString());
        assertEquals('h',s.charAt(0));
        assertEquals("ell",s.subSequence(1,4).toString());
        assertEquals(5,s.length());

    }

    @Test
    public void ShiftTest2() {
        ShiftingCharSequence s = new ShiftingCharSequence("hello");
        s.shift(0);

        assertEquals("hello",s.toString());
        assertEquals('h',s.charAt(0));
        assertEquals("ell",s.subSequence(1,4).toString());
        assertEquals(5,s.length());
    }

    @Test
    public void ShiftTest3() {
        ShiftingCharSequence s = new ShiftingCharSequence("hello");
        s.shift(0);
        s.shift(1);

        assertEquals("ello",s.toString());
        assertEquals('e',s.charAt(0));
        assertEquals("llo",s.subSequence(1,4).toString());
        assertEquals(4,s.length());
    }

    @Test
    public void ShiftTest4() {
        ShiftingCharSequence s = new ShiftingCharSequence("hello");
        s.shift(0);
        s.shift(1);
        s.shift(2);

        assertEquals("lo",s.toString());
        assertEquals('l',s.charAt(0));
        assertEquals("o",s.subSequence(1,2).toString());
        assertEquals(2,s.length());
    }

    @Test
    public void ShiftTest5() {
        ShiftingCharSequence s = new ShiftingCharSequence("hello");
        s.shift(0);
        s.shift(1);
        s.shift(2);
        s.shift(2);

        assertEquals("",s.toString());
    }

    @Test
    public void ShiftTest6() {
        ShiftingCharSequence s = new ShiftingCharSequence("hello");
        s.shift(0);
        s.shift(1);
        s.shift(2);
        s.setData("hello");
        s.shift(2);

        assertEquals("llo",s.toString());
    }
}