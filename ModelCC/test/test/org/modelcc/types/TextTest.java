package test.org.modelcc.types;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserException;
import org.modelcc.parser.fence.adapter.FenceParserFactory;
import org.modelcc.types.StringModel;

public class TextTest {

    public static Parser<?> generateParser(Class<?> source) {
         try {
            ModelReader jmr = new JavaModelReader(source);
            Model m = jmr.read();
            Set<PatternRecognizer> se = new HashSet<PatternRecognizer>();
            se.add(new RegExpPatternRecognizer("( |\n|\t|\r)+"));
            se.add(new RegExpPatternRecognizer("%.*\n"));
            return FenceParserFactory.create(m,se);
        } catch (Exception ex) {
            Logger.getLogger(TextTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return null;
        }
    }

    public static void checkMatches(Class<?> source,String input,int matches) {
    	try {
			assertEquals(matches,generateParser(source).parseAll(input).size());
		} catch (ParserException e) {
			assertEquals(0,matches);
		}
    }

    public static Object parse(Class<?> source,String input) {
    	try {
        	return generateParser(source).parse(input);
		} catch (Exception e) {
	    	return null;
		}
    }
	@Test
	public void TextsTest() {
		checkMatches(StringModel.class,"a",1);
		checkMatches(StringModel.class,"a$1!$&)=!)",1);
		checkMatches(StringModel.class,"\"a$1!$&)=!)\"",1);
		checkMatches(StringModel.class,"\"a$1!\"$&)=!)\"",0);
		checkMatches(StringModel.class,"a+",1);
		checkMatches(StringModel.class,"+8\"919",1);
		checkMatches(StringModel.class,"-",1);
		checkMatches(StringModel.class,"a    asdiof",1);
		checkMatches(StringModel.class,"",1);
		checkMatches(StringModel.class,"    asdiof",1);
		checkMatches(StringModel.class,"a;asdf",0);
		checkMatches(StringModel.class,"a;",0);
		checkMatches(StringModel.class,"a\n",1);
		checkMatches(StringModel.class,"a\nad",0);
		checkMatches(StringModel.class,"a\r",1);

		assertEquals("testvalue",((StringModel)parse(StringModel.class,"testvalue")).getValue());
		assertEquals("testvalue",((StringModel)parse(StringModel.class,"     testvalue    ")).getValue());
		assertEquals("     testvalue    ",((StringModel)parse(StringModel.class,"\"     testvalue    \"")).getValue());
		assertEquals("     testv\"alue    ",((StringModel)parse(StringModel.class,"\"     testv\\\"alue    \"")).getValue());
	}

}
