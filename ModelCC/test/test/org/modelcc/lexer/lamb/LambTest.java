/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.lexer.lamb;

import org.modelcc.lexer.Lexer;
import org.modelcc.lexer.lamb.adapter.LambLexer;
import java.io.StringReader;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.lexer.lamb.Token;
import org.modelcc.lexer.lamb.LexicalGraph;
import org.modelcc.language.lexis.TokenBuilder;
import org.modelcc.language.lexis.TokenSpecificationCyclicPrecedenceException;
import org.modelcc.language.lexis.TokenOption;
import org.modelcc.language.lexis.TokenSpecification;
import org.modelcc.language.lexis.LexicalSpecification;
import org.modelcc.language.lexis.LexicalSpecificationFactory;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;
import java.util.HashSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelcc.lexer.recognizer.PatternRecognizer;

import test.org.modelcc.io.Serialization;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class LambTest {

    public LambTest() {
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

    public static String readText(String input,Token t) {
        return input.substring(t.getStartIndex(),t.getEndIndex()+1);
    }
    public static Token searchToken(LexicalGraph lg,int start,int end,Object type) {
        Token t;
        Iterator<Token> ite;
        for (ite = lg.getTokens().iterator();ite.hasNext();) {
            t = ite.next();
            if (t.getStartIndex()==start && t.getEndIndex()==end && t.getType()==type)
                return t;
        }
        return null;
    }

  @Test
    public void OverlapCheck1() {
        TokenSpecification m1,m2,m3,m4,m5,m6;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);

        String input = "$253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        try {
            lamb = (Lexer) Serialization.testSerialize(lamb);
        } catch (ClassNotFoundException ex) {
            assertFalse(true);
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        LexicalGraph lg = lamb.scan(sr);
        try {
            lg = (LexicalGraph)Serialization.testSerialize(lg);
        } catch (ClassNotFoundException ex) {
            assertFalse(true);
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        assertEquals(4,lg.getTokens().size());
        assertEquals(2,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer")));
        assertNotNull(searchToken(lg,1,3,"Integer"));
        assertEquals("25",readText(input,searchToken(lg,1,2,"Integer2")));
        assertNotNull(searchToken(lg,1,2,"Integer2"));
        assertEquals("3",readText(input,searchToken(lg,3,3,"Integer")));
        assertNotNull(searchToken(lg,3,3,"Integer"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

    @Test
    public void PrecedenceCheck1() {
        TokenSpecification m1,m2,m3,m4,m5,m6;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);

        lsf.addPrecedence(m5,m4);

        String input = "$253+";
        StringReader sr = new StringReader(input);
        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(2,lg.getTokens().size());
        assertEquals(1,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer")));
        assertNotNull(searchToken(lg,1,3,"Integer"));
        assertNull(searchToken(lg,1,2,"Integer2"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

   @Test
    public void PrecedenceCheck2() {
        TokenSpecification m1,m2,m3,m4,m5,m6;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);

        lsf.addPrecedence(m4,m5);

        String input = "$253+";
        StringReader sr = new StringReader(input);
        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(3,lg.getTokens().size());
        assertEquals(1,lg.getStart().size());
        assertEquals("25",readText(input,searchToken(lg,1,2,"Integer2")));
        assertNotNull(searchToken(lg,1,2,"Integer2"));
        assertEquals("3",readText(input,searchToken(lg,3,3,"Integer")));
        assertNotNull(searchToken(lg,3,3,"Integer"));
        assertNull(searchToken(lg,3,3,"Integer2"));
        assertNull(searchToken(lg,1,3,"Integer"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

   @Test
    public void AnalysisCheck3() {
        TokenSpecification m1,m2,m3,m4,m5,m6;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);

        String input = "$22 +5354 +";
        StringReader sr = new StringReader(input);
        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(10,lg.getTokens().size());
        assertEquals(2,lg.getStart().size());
        assertEquals("22",readText(input,searchToken(lg,1,2,"Integer2")));
        assertNotNull(searchToken(lg,1,2,"Integer2"));
        assertEquals("22",readText(input,searchToken(lg,1,2,"Integer")));
        assertNotNull(searchToken(lg,1,2,"Integer"));
        assertEquals("+53",readText(input,searchToken(lg,4,6,"Integer2")));
        assertNotNull(searchToken(lg,4,6,"Integer2"));
        assertEquals("+5354",readText(input,searchToken(lg,4,8,"Integer")));
        assertNotNull(searchToken(lg,4,8,"Integer"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
        assertEquals("53",readText(input,searchToken(lg,5,6,"Integer2")));
        assertNotNull(searchToken(lg,5,6,"Integer2"));
        assertEquals("5354",readText(input,searchToken(lg,5,8,"Integer")));
        assertNotNull(searchToken(lg,5,8,"Integer"));
        assertEquals("54",readText(input,searchToken(lg,7,8,"Integer2")));
        assertNotNull(searchToken(lg,7,8,"Integer2"));
        assertEquals("54",readText(input,searchToken(lg,7,8,"Integer")));
        assertNotNull(searchToken(lg,7,8,"Integer"));
        assertEquals("+",readText(input,searchToken(lg,10,10,"Operator")));
        assertNotNull(searchToken(lg,10,10,"Operator"));
    }

    @Test public void AnalysisCheck4() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4;
        String input = "main main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(2,lg.getTokens().size());

    }

  @Test
    public void LinkCheck() {
        TokenSpecification m1,m2,m3,m4,m5,m6;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);

        String input = "$253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertFalse(lg.getFollowing().get(searchToken(lg,1,3,"Integer")).contains(searchToken(lg,3,3,"Integer")));
        assertTrue(lg.getFollowing().get(searchToken(lg,1,2,"Integer2")).contains(searchToken(lg,3,3,"Integer")));
        assertTrue(lg.getFollowing().get(searchToken(lg,3,3,"Integer")).contains(searchToken(lg,4,4,"Operator")));
        assertTrue(lg.getFollowing().get(searchToken(lg,1,3,"Integer")).contains(searchToken(lg,4,4,"Operator")));

        assertFalse(lg.getPreceding().get(searchToken(lg,3,3,"Integer")).contains(searchToken(lg,1,3,"Integer")));
        assertTrue(lg.getPreceding().get(searchToken(lg,3,3,"Integer")).contains(searchToken(lg,1,2,"Integer2")));
        assertTrue(lg.getPreceding().get(searchToken(lg,4,4,"Operator")).contains(searchToken(lg,3,3,"Integer")));
        assertTrue(lg.getPreceding().get(searchToken(lg,4,4,"Operator")).contains(searchToken(lg,1,3,"Integer")));

    }

  @Test
    public void PrecedenceTransitivityCheck1() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer1",new RegExpPatternRecognizer("(-|\\+)?[0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Integer3",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9][0-9]"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);

        lsf.addPrecedence(m4,m5);
        lsf.addPrecedence(m4,m7);
        lsf.addPrecedence(m5,m6);

        String input = "$253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(5,lg.getTokens().size());
        assertEquals(2,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer3")));
        assertNotNull(searchToken(lg,1,3,"Integer3"));
        assertEquals("2",readText(input,searchToken(lg,1,1,"Integer1")));
        assertNotNull(searchToken(lg,1,1,"Integer1"));
        assertEquals("5",readText(input,searchToken(lg,2,2,"Integer1")));
        assertNotNull(searchToken(lg,2,2,"Integer1"));
        assertEquals("3",readText(input,searchToken(lg,3,3,"Integer1")));
        assertNotNull(searchToken(lg,3,3,"Integer1"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

  
  @Test
    public void PrecedenceTransitivityCheck2() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer1",new RegExpPatternRecognizer("(-|\\+)?[0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Integer3",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9][0-9]"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);

        lsf.addPrecedence(m4,m5);
        lsf.addPrecedence(m4,m7);
        lsf.addPrecedence(m5,m6);

        String input = "$253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(5,lg.getTokens().size());
        assertEquals(2,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer3")));
        assertNotNull(searchToken(lg,1,3,"Integer3"));
        assertEquals("2",readText(input,searchToken(lg,1,1,"Integer1")));
        assertNotNull(searchToken(lg,1,1,"Integer1"));
        assertEquals("5",readText(input,searchToken(lg,2,2,"Integer1")));
        assertNotNull(searchToken(lg,2,2,"Integer1"));
        assertEquals("3",readText(input,searchToken(lg,3,3,"Integer1")));
        assertNotNull(searchToken(lg,3,3,"Integer1"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

  @Test
    public void PrecedenceTransitivityCheck3() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer1",new RegExpPatternRecognizer("(-|\\+)?[0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Integer3",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9][0-9]"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);

        lsf.addPrecedence(m4,m5);
        lsf.addPrecedence(m4,m7);
        lsf.addPrecedence(m5,m6);

        String input = "$253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(5,lg.getTokens().size());
        assertEquals(2,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer3")));
        assertNotNull(searchToken(lg,1,3,"Integer3"));
        assertEquals("2",readText(input,searchToken(lg,1,1,"Integer1")));
        assertNotNull(searchToken(lg,1,1,"Integer1"));
        assertEquals("5",readText(input,searchToken(lg,2,2,"Integer1")));
        assertNotNull(searchToken(lg,2,2,"Integer1"));
        assertEquals("3",readText(input,searchToken(lg,3,3,"Integer1")));
        assertNotNull(searchToken(lg,3,3,"Integer1"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

  @Test
    public void PrecedenceTransitivityCheck4() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer1",new RegExpPatternRecognizer("(-|\\+)?[0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Integer3",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9][0-9]"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);

        lsf.addPrecedence(m4,m5);
        lsf.addPrecedence(m4,m7);
        lsf.addPrecedence(m5,m6);

        String input = "$253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(5,lg.getTokens().size());
        assertEquals(2,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer3")));
        assertNotNull(searchToken(lg,1,3,"Integer3"));
        assertEquals("2",readText(input,searchToken(lg,1,1,"Integer1")));
        assertNotNull(searchToken(lg,1,1,"Integer1"));
        assertEquals("5",readText(input,searchToken(lg,2,2,"Integer1")));
        assertNotNull(searchToken(lg,2,2,"Integer1"));
        assertEquals("3",readText(input,searchToken(lg,3,3,"Integer1")));
        assertNotNull(searchToken(lg,3,3,"Integer1"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

  @Test
    public void PrecedenceTransitivityCheck5() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer1",new RegExpPatternRecognizer("(-|\\+)?[0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Integer3",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9][0-9]"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);

        lsf.addPrecedence(m4,m5);
        lsf.addPrecedence(m4,m7);
        lsf.addPrecedence(m5,m6);

        String input = "$253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(5,lg.getTokens().size());
        assertEquals(2,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer3")));
        assertNotNull(searchToken(lg,1,3,"Integer3"));
        assertEquals("2",readText(input,searchToken(lg,1,1,"Integer1")));
        assertNotNull(searchToken(lg,1,1,"Integer1"));
        assertEquals("5",readText(input,searchToken(lg,2,2,"Integer1")));
        assertNotNull(searchToken(lg,2,2,"Integer1"));
        assertEquals("3",readText(input,searchToken(lg,3,3,"Integer1")));
        assertNotNull(searchToken(lg,3,3,"Integer1"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

  @Test
    public void IgnoreCheck1() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Spaceinteger",new RegExpPatternRecognizer(" [0-9]+"),TokenOption.IGNORE,null);
        m5 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);

        String input = " 253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(1,lg.getTokens().size());
        assertEquals(1,lg.getStart().size());
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

  @Test
    public void IgnoreCheck2() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Spaceinteger",new RegExpPatternRecognizer(" [0-9]+"),TokenOption.IGNORE,null);
        m5 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);

        lsf.addPrecedence(m1,m4);

        String input = " 253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(2,lg.getTokens().size());
        assertEquals(1,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer")));
        assertNotNull(searchToken(lg,1,3,"Integer"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }


  @Test
    public void IgnoreCheck3() {
        TokenSpecification m1,m2,m3,m4,m5,m6;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        Set<PatternRecognizer> ignore = new HashSet<PatternRecognizer>();
        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer2",new RegExpPatternRecognizer("(-|\\+)?[0-9][0-9]"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);

        ignore.add(new RegExpPatternRecognizer(" +"));
        ignore.add(new RegExpPatternRecognizer("\\t+"));
        ignore.add(new RegExpPatternRecognizer("\\n|\\r+"));
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);

        String input = "$253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls,ignore);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(4,lg.getTokens().size());
        assertEquals(2,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer")));
        assertNotNull(searchToken(lg,1,3,"Integer"));
        assertEquals("25",readText(input,searchToken(lg,1,2,"Integer2")));
        assertNotNull(searchToken(lg,1,2,"Integer2"));
        assertEquals("3",readText(input,searchToken(lg,3,3,"Integer")));
        assertNotNull(searchToken(lg,3,3,"Integer"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
    }

  @Test
    public void BuilderCheck1() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        Set<TokenSpecification> prec1 = new HashSet<TokenSpecification>();
        Set<TokenSpecification> prec2 = new HashSet<TokenSpecification>();
        Set<TokenSpecification> prec3 = new HashSet<TokenSpecification>();

        TokenBuilder tb = new TokenBuilder() {

            public boolean build(Token t) {
                return false;
            }

        };
        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,tb);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);

        String input = " 253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(1,lg.getTokens().size());
        assertEquals(1,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer")));
        assertNotNull(searchToken(lg,1,3,"Integer"));
    }


  @Test
    public void BuilderCheck2() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        Set<TokenSpecification> prec1 = new HashSet<TokenSpecification>();
        Set<TokenSpecification> prec2 = new HashSet<TokenSpecification>();
        Set<TokenSpecification> prec3 = new HashSet<TokenSpecification>();

        TokenBuilder tb = new TokenBuilder() {

            public boolean build(Token t) {
                return true;
            }

        };
        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,tb);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);

        String input = " 253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(2,lg.getTokens().size());
        assertEquals(1,lg.getStart().size());
        assertEquals("253",readText(input,searchToken(lg,1,3,"Integer")));
        assertNotNull(searchToken(lg,1,3,"Integer"));
        assertEquals("+",readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(searchToken(lg,4,4,"Operator"));
   }

  private class CountTokenBuilder extends TokenBuilder {

        private int count = 0;

        @Override
        public boolean build(Token t) {
            count++;
            return true;
        }

        public int getCount() {
            return count;
        }

    }
  
  @Test
    public void BuilderCountCheck() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        Set<TokenSpecification> prec1 = new HashSet<TokenSpecification>();
        Set<TokenSpecification> prec2 = new HashSet<TokenSpecification>();
        Set<TokenSpecification> prec3 = new HashSet<TokenSpecification>();

        CountTokenBuilder tb = new CountTokenBuilder();
        
        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,tb);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);

        String input = " 253+";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(1,tb.getCount());
    }


  @Test
    public void RemoveCheck() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Spaceinteger",new RegExpPatternRecognizer(" [0-9]+"),TokenOption.IGNORE,null);
        m5 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Operator",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Anything",new RegExpPatternRecognizer("."),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m7);
        lsf.removeTokenSpecification(m7);

        lsf.addPrecedence(m1,m4);
        lsf.addPrecedence(m4,m1);
        lsf.removePrecedence(m4,m1);

        String input = " 253+     ";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(2,lg.getTokens().size());
        assertEquals(1,lg.getStart().size());
        assertEquals("253",LambTest.readText(input,searchToken(lg,1,3,"Integer")));
        assertNotNull(LambTest.searchToken(lg,1,3,"Integer"));
        assertEquals("+",LambTest.readText(input,searchToken(lg,4,4,"Operator")));
        assertNotNull(LambTest.searchToken(lg,4,4,"Operator"));
   }


  @Test
    public void ChainedPrecedenceCheck() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("A",new RegExpPatternRecognizer("abc"),TokenOption.CONSIDER,null);
        m2 = new TokenSpecification("B",new RegExpPatternRecognizer("abc"),TokenOption.CONSIDER,null);
        m3 = new TokenSpecification("C",new RegExpPatternRecognizer("abc"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);

        lsf.addPrecedence(m1,m2);
        lsf.addPrecedence(m2,m3);

        String input = "abc";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

        assertEquals(2,lg.getTokens().size());
        assertEquals(2,lg.getStart().size());
        assertNotNull(searchToken(lg,0,2,"A"));
        assertEquals("abc",readText(input,searchToken(lg,0,2,"A")));
        assertNotNull(searchToken(lg,0,2,"C"));
        assertEquals("abc",readText(input,searchToken(lg,0,2,"C")));
   }

  @Test
    public void IgnoreCheck() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("(",new RegExpPatternRecognizer("\\("),TokenOption.CONSIDER,null);
        m2 = new TokenSpecification(")",new RegExpPatternRecognizer("\\)"),TokenOption.CONSIDER,null);
        m3 = new TokenSpecification("+",new RegExpPatternRecognizer("\\+"),TokenOption.CONSIDER,null);
        m4 = new TokenSpecification("-",new RegExpPatternRecognizer("-"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("/",new RegExpPatternRecognizer("\\/"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("*",new RegExpPatternRecognizer("\\*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("int",new RegExpPatternRecognizer("[0-9]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("(+",new RegExpPatternRecognizer("\\(\\+"),TokenOption.IGNORE,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);

        lsf.addPrecedence(m1,m2);
        lsf.addPrecedence(m2,m3);

        String input = "3+(2+5)";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);

   }

  @Test
    public void IgnoreCheckComplete() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("(",new RegExpPatternRecognizer("\\("),TokenOption.CONSIDER,null);
        m2 = new TokenSpecification(")",new RegExpPatternRecognizer("\\)"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);

        String input = "( )";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
        se.add(new RegExpPatternRecognizer("[ \n\r\t]+"));
        Lexer lamb = new LambLexer(ls,se);
        LexicalGraph lg = lamb.scan(sr);
        assertNotNull(lg.getFollowing().get(lg.getStart().iterator().next()));

   }

  @Test
    public void IgnoreCheckInComplete() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();

        m1 = new TokenSpecification("(",new RegExpPatternRecognizer("\\("),TokenOption.CONSIDER,null);
        m2 = new TokenSpecification(")",new RegExpPatternRecognizer("\\)"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);

        String input = "( )";
        StringReader sr = new StringReader(input);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        Lexer lamb = new LambLexer(ls);
        LexicalGraph lg = lamb.scan(sr);
        assertNull(lg.getFollowing().get(lg.getStart().iterator().next()));

   }

      /*
        for (int i = 0;i < lg.getTokens().size();i++) {
            Token tk = lg.getTokens().get(i);
            System.out.println("Token of type "+tk.getType()+": "+tk)+" ("+tk.getStartIndex()+"-"+tk.getEndIndex()+")");
        }*/

}