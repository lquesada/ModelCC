/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.lexer.recognizer;

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
public class PatternRecognizerTest {

    public PatternRecognizerTest() {
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
    public void ObjectMatchCheck1() {
        PatternRecognizer pr = new PatternRecognizer("a") {

            @Override
			public MatchedObject read(CharSequence cs, int start) {
                if (cs.subSequence(0,5).equals("hello"))
                    return new MatchedObject(new Integer(1),"hello");
                else
                    return null;
            }
        };

        MatchedObject match = pr.read("hello",0);
        assertEquals("hello",match.getText());
        assertEquals(new Integer(1),match.getObject());

    }

    @Test
    public void ObjectMatchCheck2() {
        PatternRecognizer pr = new PatternRecognizer("a") {

            @Override
			public MatchedObject read(CharSequence cs, int start) {
                if (cs.subSequence(0,5).equals("hello"))
                    return new MatchedObject(new Integer(1),"hello");
                else
                    return null;
            }
        };

        MatchedObject match = pr.read("hella",0);
        assertNull(match);

    }


}
