/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */

package test.languages.awk;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

public class AWKTests {

	Parser<AWKField> awkFieldParser;
	Parser<AWKExpression> awkExpressionParser;
	Parser<AWKPrintStatement> awkPrintStatementParser;
	Parser<AWKStatement> awkStatementParser;
	Parser<AWKAction> awkActionParser;
	Parser<AWKRegularExpressionPattern> awkRegularExpressionPatternParser;
	Parser<AWKPattern> awkPatternParser;
	Parser<AWKProgram> awkProgramParser;
	Parser<AWKRule> awkRuleParser;

	public Parser generateParser(Class cl) {
		Model model;
		Parser parser = null;
		try {
	        model = JavaModelReader.read(cl);
	        parser = ParserFactory.create(model,ParserFactory.WHITESPACE);
		} catch (Exception e) {
			e.printStackTrace();
			assertTrue(false);
		}
		return parser;
	}

	private void assertValid(Parser parser, String string) {
		Collection ret = null;
    	try {
    		ret = parser.parseAll(string);
    	} catch (Exception e) {
    		e.printStackTrace();
        	assertFalse(true);
    	}
	
	}

	private void assertInvalid(Parser parser, String string) {
    	try {
    		parser.parseAll(string);
    	} catch (Exception e) {
    		return;
    	}
    	assertFalse(true);
	}
	
	@Before
	public void before() {
		awkFieldParser = generateParser(AWKField.class);
		awkExpressionParser = generateParser(AWKExpression.class);
		awkPrintStatementParser = generateParser(AWKPrintStatement.class);
		awkStatementParser = generateParser(AWKStatement.class);
		awkActionParser = generateParser(AWKAction.class);
		awkRegularExpressionPatternParser = generateParser(AWKRegularExpressionPattern.class);
		awkPatternParser = generateParser(AWKPattern.class);
		awkRuleParser = generateParser(AWKRule.class);
		
		awkProgramParser = generateParser(AWKProgram.class);
	}
	
	@Test
	public void awkFieldTest() {
		assertValid(awkFieldParser,"$0");
		assertValid(awkFieldParser,"$1");
		assertInvalid(awkFieldParser,"0");
		assertInvalid(awkFieldParser,"1");
	}
	@Test
	public void awkExpressionTest() {
		assertValid(awkExpressionParser,"$0");
		assertValid(awkExpressionParser,"$1");
		assertInvalid(awkExpressionParser,"0");
		assertInvalid(awkExpressionParser,"1");
	}
	@Test
	public void awkPrintStatementTest() {
		assertValid(awkPrintStatementParser,"print $0");
		assertValid(awkPrintStatementParser,"print $1");
		assertInvalid(awkPrintStatementParser,"print 0");
		assertInvalid(awkPrintStatementParser,"$0");
	}
	@Test
	public void awkStatementTest() {
		assertValid(awkStatementParser,"print $0");
		assertValid(awkStatementParser,"print $1");
		assertInvalid(awkStatementParser,"print 0");
		assertInvalid(awkStatementParser,"$0");
	}
	@Test
	public void awkActionTest() {
		assertValid(awkActionParser,"{ }");
		assertValid(awkActionParser,"{ print $1 }");
		assertValid(awkActionParser,"{print $1}");
		assertInvalid(awkActionParser,"{ 0 }");
		assertInvalid(awkActionParser,"$0");
	}
	@Test
	public void awkRegularExpressionPatternTest() {
		assertValid(awkRegularExpressionPatternParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
		assertValid(awkRegularExpressionPatternParser,"//");
		assertValid(awkRegularExpressionPatternParser,"/a/");
		assertInvalid(awkRegularExpressionPatternParser,"/aa/aa/");
		assertInvalid(awkRegularExpressionPatternParser,"/");
		assertInvalid(awkRegularExpressionPatternParser,"afa");
	}
 	@Test
	public void awkPatternTest() {
		assertValid(awkPatternParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
		assertValid(awkPatternParser,"//");
		assertValid(awkPatternParser,"/a/");
		assertInvalid(awkPatternParser,"/aa/aa/");
		assertInvalid(awkPatternParser,"/");
		assertInvalid(awkPatternParser,"afa");
	}

	@Test
	public void awkRuleTest() {
		assertValid(awkRuleParser,"/[a-zA-Z][a-zA-Z0-9_]+/ { print $1 }");
		assertValid(awkRuleParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
		assertValid(awkRuleParser,"{ print $1 }");
		assertInvalid(awkRuleParser,"/aaaa/ /aa/");
		assertInvalid(awkRuleParser,"{ $1 } { $1 }");
	}

	@Test
	public void awkProgramTest() {
		assertValid(awkProgramParser,"// { print $1 }");
		assertValid(awkProgramParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
		assertValid(awkProgramParser,"{ print $1 }");
		assertValid(awkProgramParser,"/aaaa/ \n /aa/");
		assertValid(awkProgramParser,"{ print $1 } \n { print $1 }");
	}
}
