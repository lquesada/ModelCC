package test.languages.cswebunit;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.fence.adapter.FenceParserGenerator;

public class ScanTest {

    public static Parser<?> generateParser(Class<?> source) {
         try {
            ModelReader jmr = new JavaModelReader(source);
            Model m = jmr.read();
            return FenceParserGenerator.create(m);
        } catch (Exception ex) {
            Logger.getLogger(ScanTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return null;
        }
    }

    public static void checkMatches(Class<?> source,String input,int matches) {
    	assertEquals(matches,generateParser(source).parseAll(input).size());
    }

    public static  Object parse(Class<?> source,String input) {
    	return generateParser(source).parse(input);
    }
    
    /*TODO
	@Test
	public void StringTest() {
		checkMatches(BigString.class,"01a2",1);
		checkMatches(BigString.class,"012",1);
		
	}*/

	@Test
	public void StringContentTest() {
		System.out.println("START1");
		BigString a = (BigString)parse(BigString.class,"01a2");
		OptionalString value = a.getValue();
		assertEquals(value,"a");
		
		
	}

	@Test
	public void StringNotContentTest() {
		System.out.println("START2");
		BigString a = (BigString)parse(BigString.class,"012");
		OptionalString value = a.getValue();
		assertEquals(value,"");
		
		
	}

}
