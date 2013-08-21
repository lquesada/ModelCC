/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.csm;


import org.modelcc.AssociativityType;
import org.modelcc.CompositionType;
import org.modelcc.csm.CSM;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.*;


import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
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


    @Test
    public void CSMTest8() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = CSMapply(m,"UnaryExpression prefix=\"a\",\"b\" suffix=\"a\",\"b\";");
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getPrefix().get(0)).getRegExp());
        	assertEquals("b",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getPrefix().get(1)).getRegExp());
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getSuffix().get(0)).getRegExp());
        	assertEquals("b",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getSuffix().get(1)).getRegExp());
    }


    @Test
    public void CSMTest9() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = CSMapply(m,"UnaryExpression prefix=\"a\",\"b\"; UnaryExpression suffix=\"a\",\"b\";");
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getPrefix().get(0)).getRegExp());
        	assertEquals("b",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getPrefix().get(1)).getRegExp());
        	assertEquals("a",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getSuffix().get(0)).getRegExp());
        	assertEquals("b",((RegExpPatternRecognizer)((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getSuffix().get(1)).getRegExp());
    }

    @Test
    public void CSMTest10() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = null;
    		m2 = CSMapply(m,"UnaryExpression associativity=lefttoright;");
        	assertEquals(AssociativityType.LEFT_TO_RIGHT,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getAssociativity());
    		m2 = CSMapply(m,"UnaryExpression associativity=righttoleft;");
        	assertEquals(AssociativityType.RIGHT_TO_LEFT,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getAssociativity());
    		m2 = CSMapply(m,"UnaryExpression associativity=undefined;");
        	assertEquals(AssociativityType.UNDEFINED,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getAssociativity());
    		m2 = CSMapply(m,"UnaryExpression associativity=nonassociative;");
        	assertEquals(AssociativityType.NON_ASSOCIATIVE,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getAssociativity());
    		m2 = CSMapply(m,"UnaryExpression associativity=UNDEFINED;");
        	assertEquals(AssociativityType.UNDEFINED,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getAssociativity());
    		m2 = CSMapply(m,"UnaryExpression associativity=nonAssociative;");
        	assertEquals(AssociativityType.NON_ASSOCIATIVE,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getAssociativity());
    }

    @Test
    public void CSMTest11() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = null;
    		m2 = CSMapply(m,"UnaryExpression composition=lazy;");
        	assertEquals(CompositionType.LAZY,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getComposition());
    		m2 = CSMapply(m,"UnaryExpression composition=eager;");
        	assertEquals(CompositionType.EAGER,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getComposition());
    		m2 = CSMapply(m,"UnaryExpression composition=undefined;");
        	assertEquals(CompositionType.UNDEFINED,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getComposition());
    		m2 = CSMapply(m,"UnaryExpression composition=explicit;");
        	assertEquals(CompositionType.EXPLICIT,((ComplexModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.UnaryExpression.class)).getComposition());
    }

    @Test
    public void CSMTest12() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = null;
    		m2 = CSMapply(m,"IntegerLiteral pattern=\"[1-9]*\";");
        	assertEquals("[1-9]*",((RegExpPatternRecognizer)((BasicModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.literals.IntegerLiteral.class)).getPattern()).getRegExp());
    }

    @Test
    public void CSMTest13() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = null;
    		m2 = CSMapply(m,"IntegerLiteral pattern=(org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer:\"[1-9]*\");");
        	assertEquals("[1-9]*",((RegExpPatternRecognizer)((BasicModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.literals.IntegerLiteral.class)).getPattern()).getRegExp());
    }

    @Test
    public void CSMTest14() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = null;
    		m2 = CSMapply(m,"IntegerLiteral pattern=(org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer:\"\");");
        	assertEquals("",((RegExpPatternRecognizer)((BasicModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.literals.IntegerLiteral.class)).getPattern()).getRegExp());
    }
    
    @Test
    public void CSMTest15() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = null;
    		m2 = CSMapply(m,"IntegerLiteral pattern=(org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer);");
        	assertEquals("",((RegExpPatternRecognizer)((BasicModelElement)m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.literals.IntegerLiteral.class)).getPattern()).getRegExp());
    }

    @Test
    public void CSMTest16() {
    		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    		Model m2 = null;
    		m2 = CSMapply(m,"IntegerLiteral precedes=RealLiteral,BinaryOperator;");
        	assertTrue(m2.getPrecedences().get(m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.literals.IntegerLiteral.class)).contains(m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.literals.RealLiteral.class)));
        	assertTrue(m2.getPrecedences().get(m2.getClassToElement().get(test.languages.arithmeticcalculator.expressions.literals.IntegerLiteral.class)).contains(m2.getClassToElement().get(test.languages.arithmeticcalculator.BinaryOperator.class)));
    }


    private class CountFilter implements Filter {

        boolean show;

        private int count;

        public CountFilter(boolean show) {
            this.show = show;
        }

        @Override
		public boolean isLoggable(LogRecord record) {
            if (record.getLevel() == Level.SEVERE) {
                count++;
            }
            if (show) {
                return true;
            }
            else
                return false;
        }

        int getCount() { return count; }

    }
    
    @Test
    public void CSMWrongTest1() {
        Logger lg = Logger.getLogger(CSM.class.getName());
        CountFilter c = new CountFilter(false);
        lg.setFilter(c);
        Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
    	Model m2 = null;
    	m2 = CSMapply(m,"IntegerLiteral precedes=RealLiterl;");
        assertEquals(1,c.getCount());
    }
    @Test
    public void CSMWrongTest2() {
        Logger lg = Logger.getLogger(CSM.class.getName());
        CountFilter c = new CountFilter(false);
        lg.setFilter(c);
		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
		Model m2 = null;
		m2 = CSMapply(m,"IntegerLiteral precedes=IntegerLiteral;");
        assertEquals(1,c.getCount());
    }

    @Test
    public void CSMWrongTest3() {
        Logger lg = Logger.getLogger(CSM.class.getName());
        CountFilter c = new CountFilter(false);
        lg.setFilter(c);
		Model m = modelGen(test.languages.arithmeticcalculator.Expression.class);
		Model m2 = null;
		m2 = CSMapply(m,"IntegerLiteral precedes=RealLiteral;RealLiteral precedes=BinaryOperator;BinaryOperator precedes=IntegerLiteral;");
        assertEquals(1,c.getCount());
    }

    @Test
    public void CSMWrongTest4() {
        Logger lg = Logger.getLogger(CSM.class.getName());
        CountFilter c = new CountFilter(false);
        lg.setFilter(c);
		Model m = modelGen(test.languages.dup.Main.class);
		Model m2 = null;
		m2 = CSMapply(m,"ClassDup prefix=\"a\";");
        assertEquals(1,c.getCount());
    }

    @Test
    public void CSMWrongTest5() {
        Logger lg = Logger.getLogger(CSM.class.getName());
        CountFilter c = new CountFilter(false);
        lg.setFilter(c);
		Model m = modelGen(test.languages.dup.Main.class);
		Model m2 = null;
		m2 = CSMapply(m,"Main precedes=ClassDup2;");
        assertEquals(1,c.getCount());
    }

    @Test
    public void CSMWrongTest6() {
        Logger lg = Logger.getLogger(CSM.class.getName());
        CountFilter c = new CountFilter(false);
        lg.setFilter(c);
		Model m = modelGen(test.languages.dup.Main.class);
		Model m2 = null;
		m2 = CSMapply(m,"Maan precedes=ClassDup2;");
        assertEquals(1,c.getCount());
    }

    @Test
    public void CSMWrongTest7() {
        Logger lg = Logger.getLogger(CSM.class.getName());
        CountFilter c = new CountFilter(false);
        lg.setFilter(c);
		Model m = modelGen(test.languages.dup.Main.class);
		Model m2 = null;
		m2 = CSMapply(m,"Main[okx] prefix=\"a\";");
        assertEquals(1,c.getCount());
    }

}