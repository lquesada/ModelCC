/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.optionalstring2;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserException;
import org.modelcc.parser.fence.adapter.FenceParserFactory;

/**
 *
 * @author elezeta
 */
public class ScanTest {

    public static Parser<?> generateParser(Class<?> source) {
         try {
            ModelReader jmr = new JavaModelReader(source);
            Model m = jmr.read();
            return FenceParserFactory.create(m);
        } catch (Exception ex) {
            Logger.getLogger(ScanTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return null;
        }
    }

    public static void checkMatches(Class<?> source,String input,int matches) throws ParserException {
    	assertEquals(matches,generateParser(source).parseAll(input).size());
    }

    public static  Object parse(Class<?> source,String input) throws ParserException {
    	return generateParser(source).parse(input);
    }
    
	@Test
	public void StringTest() throws ParserException {
		checkMatches(BigString.class,"012",1);
		
	}

	@Test
	public void StringNotContentTest() throws ParserException {
		BigString a = (BigString)parse(BigString.class,"012");
		OptionalString value = a.getValue();
		assertEquals("",value.getValue());
	}

}
