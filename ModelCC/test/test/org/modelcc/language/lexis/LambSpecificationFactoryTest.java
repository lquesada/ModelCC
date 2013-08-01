/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.language.lexis;

import org.modelcc.language.lexis.LexicalSpecificationFactory;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.language.lexis.TokenSpecificationCyclicPrecedenceException;
import org.modelcc.language.lexis.TokenOption;
import org.modelcc.language.lexis.TokenSpecification;
import org.modelcc.language.lexis.LexicalSpecification;
import java.util.Set;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.org.modelcc.io.Serialization;
import static org.junit.Assert.*;
/**
 *
 * @author elezeta
 */
public class LambSpecificationFactoryTest {


    public LambSpecificationFactoryTest() {
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
    public void BuildCheck() {
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

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(LambSpecificationFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        assertTrue(ls.getTokenSpecifications().contains(m1));
        assertTrue(ls.getTokenSpecifications().contains(m2));
        assertTrue(ls.getTokenSpecifications().contains(m3));
        assertTrue(ls.getTokenSpecifications().contains(m4));
        assertTrue(ls.getTokenSpecifications().contains(m5));
        assertTrue(ls.getTokenSpecifications().contains(m6));
        assertTrue(ls.getPrecedences().get(m5).contains(m4));

    }

  @Test
    public void CyclicPrecedenceCheck1() {
        TokenSpecification m1,m2,m3,m4,m5,m6;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        new HashSet<TokenSpecification>();
        new HashSet<TokenSpecification>();

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
        lsf.addPrecedence(m4,m1);
        lsf.addPrecedence(m5,m6);

        try {
            lsf = (LexicalSpecificationFactory) Serialization.testSerialize(lsf);
        } catch (ClassNotFoundException ex) {
            assertTrue(false);
            Logger.getLogger(LambSpecificationFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            lsf.create();
            assertTrue(false);
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            assertFalse(false);
        }

    }

  @Test
    public void CyclicPrecedenceCheck2() {
        TokenSpecification m1,m2,m3,m4,m5,m6;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        new HashSet<TokenSpecification>();
        new HashSet<TokenSpecification>();
        new HashSet<TokenSpecification>();

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
        lsf.addPrecedence(m2,m1);
        lsf.addPrecedence(m4,m2);

        try {
            lsf.create();
            assertTrue(false);
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            assertFalse(false);
        }

    }

}
