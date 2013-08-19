/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.csm;


import org.modelcc.csm.CSM;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.*;


import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class CSMTest {

    private Model modelGen(Class cl) {
        JavaModelReader jmr = new JavaModelReader(cl);
        Model m = null;

        try {
            m = jmr.read();
        } catch (Exception ex) {
            Logger.getLogger(CSMTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return null;
        }
        return m;
    }

	private Model CSMapply(Model m, String string) {
    	try {
    		return CSM.apply(m,string);
    	} catch (Exception e) {
    		e.printStackTrace();
    		assertTrue(false);
    	}
    	return null; 
	}
	
    @Test
    public void CSMTest1() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = CSMapply(m,"BinaryExpression freeorder=true;");
        	assertTrue(((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.BinaryExpression.class)).isFreeOrder());
    }

    @Test
    public void CSMTest2() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = CSMapply(m,"UnaryExpression[op] prefix=\"a\";");
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getContents().get(0).getPrefix().get(0)).getRegExp());
    }

    @Test
    public void CSMTest3() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = CSMapply(m,"UnaryExpression[op] suffix=\"a\",\"b\";");
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getContents().get(0).getSuffix().get(0)).getRegExp());
        	assertEquals("b",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getContents().get(0).getSuffix().get(1)).getRegExp());
    }

    @Test
    public void CSMTest4() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = CSMapply(m,"UnaryExpression[op] separator=\"a\",\"b\";");
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getContents().get(0).getSeparator().get(0)).getRegExp());
        	assertEquals("b",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getContents().get(0).getSeparator().get(1)).getRegExp());
    }

    @Test
    public void CSMTest5() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = CSMapply(m,"UnaryExpression prefix=\"a\",\"b\";");
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getPrefix().get(0)).getRegExp());
        	assertEquals("b",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getPrefix().get(1)).getRegExp());
    }

    @Test
    public void CSMTest6() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = CSMapply(m,"UnaryExpression suffix=\"a\",\"b\";");
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getSuffix().get(0)).getRegExp());
        	assertEquals("b",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getSuffix().get(1)).getRegExp());
    }
    
    @Test
    public void CSMTest7() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = CSMapply(m,"UnaryExpression separator=\"a\",\"b\";");
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getSeparator().get(0)).getRegExp());
        	assertEquals("b",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getSeparator().get(1)).getRegExp());
    }

}