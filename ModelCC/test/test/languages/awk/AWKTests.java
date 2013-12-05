/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.awk;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserException;
import org.modelcc.parser.ParserFactory;
import org.modelcc.parser.fence.Symbol;

public class AWKTests {

	static Parser<AWKField> awkFieldParser;
	static Parser<AWKExpression> awkExpressionParser;
	static Parser<AWKPrintStatement> awkPrintStatementParser;
	static Parser<AWKStatement> awkStatementParser;
	static Parser<AWKAction> awkActionParser;
	static Parser<AWKRegularExpressionPattern> awkRegularExpressionPatternParser;
	static Parser<AWKPattern> awkPatternParser;
	static Parser<AWKProgram> awkProgramParser;
	static Parser<AWKRule> awkRuleParser;

	public static Parser generateParser(Class cl) {
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

	private void assertAmbiguityFree(Parser parser, String string) {
		Collection ret = null;
    	try {
    		ret = parser.parseAll(string);
    		assertEquals(1,ret.size());
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
	
	@BeforeClass
	public static void before() {
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
	public void awkFieldValidTest1() {
		assertAmbiguityFree(awkFieldParser,"$0");
	}

	@Test
	public void awkFieldValidTest2() {
		assertAmbiguityFree(awkFieldParser,"$1");
	}

	@Test
	public void awkFieldInvalidTest1() {
		assertInvalid(awkFieldParser,"0");
	}

	@Test
	public void awkFieldInvalidTest2() {
		assertInvalid(awkFieldParser,"1");
	}

	@Test
	public void awkExpressionValidTest1() {
		assertAmbiguityFree(awkExpressionParser,"$0");
	}

	@Test
	public void awkExpressionValidTest2() {
		assertAmbiguityFree(awkExpressionParser,"$1");
	}

	@Test
	public void awkExpressionInvalidTest1() {
		assertInvalid(awkExpressionParser,"0");
	}

	@Test
	public void awkExpressionInvalidTest2() {
		assertInvalid(awkExpressionParser,"1");
	}

	@Test
	public void awkPrintStatementValidTest1() {
		assertAmbiguityFree(awkPrintStatementParser,"print $0");
	}

	@Test
	public void awkPrintStatementValidTest2() {
		assertAmbiguityFree(awkPrintStatementParser,"print $1");
	}

	@Test
	public void awkPrintStatementInvalidTest1() {
		assertInvalid(awkPrintStatementParser,"print 0");
	}

	@Test
	public void awkPrintStatementInvalidTest2() {
		assertInvalid(awkPrintStatementParser,"$0");
	}

	@Test
	public void awkStatementValidTest1() {
		assertAmbiguityFree(awkStatementParser,"print $0");
	}

	@Test
	public void awkStatementValidTest2() {
		assertAmbiguityFree(awkStatementParser,"print $1");
}
	
	@Test
	public void awkStatementInvalidTest1() {
		assertInvalid(awkStatementParser,"print 0");
	}
	
	@Test
	public void awkStatementInvalidTest2() {
		assertInvalid(awkStatementParser,"$0");
	}
	
	@Test
	public void awkActionValidTest1() {
		assertAmbiguityFree(awkActionParser,"{ }");
	}
	
	@Test
	public void awkActionValidTest2() {
		assertAmbiguityFree(awkActionParser,"{ print $1 }");
	}

	@Test
	public void awkActionValidTest3() {
		assertAmbiguityFree(awkActionParser,"{print $1}");
	}

	@Test
	public void awkActionInvalidTest1() {
		assertInvalid(awkActionParser,"{ 0 }");
	}

	@Test
	public void awkActionInvalidTest2() {
		assertInvalid(awkActionParser,"$0");
	}

	@Test
	public void awkRegularExpressionPatternValidTest1() {
		assertAmbiguityFree(awkRegularExpressionPatternParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
	}

	@Test
	public void awkRegularExpressionPatternValidTest2() {
		assertAmbiguityFree(awkRegularExpressionPatternParser,"//");
	}

	@Test
	public void awkRegularExpressionPatternValidTest3() {
		assertAmbiguityFree(awkRegularExpressionPatternParser,"/a/");
	}

	@Test
	public void awkRegularExpressionPatternInvalidTest1() {
		assertInvalid(awkRegularExpressionPatternParser,"/aa/aa/");
	}

	@Test
	public void awkRegularExpressionPatternInvalidTest2() {
		assertInvalid(awkRegularExpressionPatternParser,"/");
	}

	@Test
	public void awkRegularExpressionPatternInvalidTest3() {
		assertInvalid(awkRegularExpressionPatternParser,"afa");
	}

	@Test
	public void awkPatternValidTest1() {
		assertAmbiguityFree(awkPatternParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
	}

	@Test
	public void awkPatternValidTest2() {
		assertAmbiguityFree(awkPatternParser,"//");
	}

	@Test
	public void awkPatternValidTest3() {
		assertAmbiguityFree(awkPatternParser,"/a/");
	}

	@Test
	public void awkPatternInvalidTest1() {
		assertInvalid(awkPatternParser,"/aa/aa/");
	}

	@Test
	public void awkPatternInvalidTest2() {
		assertInvalid(awkPatternParser,"/");
	}

	@Test
	public void awkPatternInvalidTest3() {
		assertInvalid(awkPatternParser,"afa");
	}

	@Test
	public void awkRuleValidTest1() {
		assertAmbiguityFree(awkRuleParser,"/[a-zA-Z][a-zA-Z0-9_]+/ { print $1 }");
	}

	@Test
	public void awkRuleValidTest2() {
		assertAmbiguityFree(awkRuleParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
	}

	@Test
	public void awkRuleValidTest3() {
		assertAmbiguityFree(awkRuleParser,"{ print $1 }");
	}

	@Test
	public void awkRuleInvalidTest1() {
		assertInvalid(awkRuleParser,"/aaaa/ /aa/");
	}

	@Test
	public void awkRuleInvalidTest2() {
		assertInvalid(awkRuleParser,"{ $1 } { $1 }");
	}

	@Test
	public void awkProgramValidTest1() {
		assertAmbiguityFree(awkProgramParser,"// { print $1 }");
	}

	@Test
	public void awkProgramValidTest2() {
		assertAmbiguityFree(awkProgramParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
	}

	@Test
	public void awkProgramValidTest3() {
		assertAmbiguityFree(awkProgramParser,"{ print $1 }");
	}

	@Test
	public void awkProgramValidTest4() {
		assertAmbiguityFree(awkProgramParser,"/aaaa/ \n /aa/");
	}

	@Test
	public void awkProgramValidTest5() {
		assertAmbiguityFree(awkProgramParser,"{ print $1 } \n { print $1 }");
/*		try {
			Collection<AWKProgram> programs = awkProgramParser.parseAll("/aa/ { print $0 }");
			int i = 0;
			for (AWKProgram program : programs) {
				i++;
				System.out.println("");
				System.out.println("");
				System.out.println("Printing program "+i+" of "+programs.size());
				System.out.println("Program is "+program);
				Map<String, Object> s = awkProgramParser.getParsingMetadata(program);
				for (String key : s.keySet()) {
					System.out.println("    "+key+"   "+s.get(key));
				}
				Symbol symbol = (Symbol)s.get("symbol");
				System.out.println("Symbol has "+symbol.getContents().size()+" contents");
				int j = 0;
				for (Symbol content : symbol.getContents()) {
					System.out.println("    "+content+"  "+content.getType()+" with "+content.getContents().size()+" contents");
					for (Symbol content2 : content.getContents()) {
						System.out.println("        "+content2+"  "+content2.getType()+" with "+content2.getContents().size()+" contents  and "+content2.getUserData()+" "+content2.getRule());
						for (Symbol content3 : content2.getContents()) {
							System.out.println("          "+content3+"  "+content3.getType()+" with "+content3.getContents().size()+" contents  and "+content3.getUserData());
						}
					}
					j++;
				}

				System.out.println("Program has "+program.getRules().length+" rules");
				j = 0;
				for (AWKRule rule : program.getRules()) {
					j++;
					System.out.println("Rule "+j+" is "+rule+" and has "+rule.getPattern()+" "+rule.getAction());
				}
			}
		} catch (ParserException e) {
			e.printStackTrace();
		}*/ 
	}

}
