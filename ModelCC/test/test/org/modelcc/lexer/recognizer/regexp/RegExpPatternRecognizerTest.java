/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.lexer.recognizer.regexp;

import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.lexer.recognizer.MatchedObject;
import org.modelcc.lexer.recognizer.PatternRecognizer;
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
public class RegExpPatternRecognizerTest {

    public RegExpPatternRecognizerTest() {
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
    public void MatchCheck1() {
        PatternRecognizer repr = new RegExpPatternRecognizer("abc+");
        MatchedObject match = repr.read("abc",0);
        assertEquals("abc",match.getText());
    }

    @Test
    public void MatchCheck2() {
        PatternRecognizer repr = new RegExpPatternRecognizer("abc+");
        MatchedObject match = repr.read("abccc",0);
        assertEquals("abccc",match.getText());
    }

    @Test
    public void MatchCheck3() {
        PatternRecognizer repr = new RegExpPatternRecognizer("abc+");
        MatchedObject match = repr.read("abcccd",0);
        assertEquals("abccc",match.getText());
    }

    @Test
    public void MatchCheck4() {
        PatternRecognizer repr = new RegExpPatternRecognizer("abc+");
        MatchedObject match = repr.read("aabcccd",0);
        assertNull(match);
    }

    @Test
    public void MatchCheck5() {
        PatternRecognizer repr = new RegExpPatternRecognizer("abc+");
        MatchedObject match = repr.read("aabcccd",1);
        assertEquals("abccc",match.getText());
    }

    @Test
    public void MatchCheck6() {
        PatternRecognizer repr = new RegExpPatternRecognizer("abc+");
        MatchedObject match = repr.read("aab",1);
        assertNull(match);
    }

    @Test
    public void MatchCheck7() {
        PatternRecognizer repr = new RegExpPatternRecognizer("abc*");
        MatchedObject match = repr.read("aab",1);
        assertEquals("ab",match.getText());
    }

    @Test
    public void MatchCheck8() {
        PatternRecognizer repr = new RegExpPatternRecognizer("(ab)+c*");
        MatchedObject match = repr.read("aabab",0);
        assertNull(match);
    }

    @Test
    public void MatchCheck9() {
        PatternRecognizer repr = new RegExpPatternRecognizer("(ab)+c*");
        MatchedObject match = repr.read("aabab",1);
        assertEquals("abab",match.getText());
    }

    @Test
    public void MatchCheck10() {
        PatternRecognizer repr = new RegExpPatternRecognizer("(ab)+c*");
        MatchedObject match = repr.read("aababc",1);
        assertEquals("ababc",match.getText());
    }

    @Test
    public void MatchCheck11() {
        PatternRecognizer repr = new RegExpPatternRecognizer("\\{");
        MatchedObject match = repr.read("{",0);
        assertEquals("{",match.getText());
    }

    @Test
    public void MatchCheck12() {
        PatternRecognizer repr = new RegExpPatternRecognizer("\\\\\\{");
        MatchedObject match = repr.read("\\{",0);
        assertEquals("\\{",match.getText());
    }

}