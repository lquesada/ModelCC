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
		assertValid(awkFieldParser,"$0");
	}

	@Test
	public void awkFieldValidTest2() {
		assertValid(awkFieldParser,"$1");
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
		assertValid(awkExpressionParser,"$0");
	}

	@Test
	public void awkExpressionValidTest2() {
		assertValid(awkExpressionParser,"$1");
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
		assertValid(awkPrintStatementParser,"print $0");
	}

	@Test
	public void awkPrintStatementValidTest2() {
		assertValid(awkPrintStatementParser,"print $1");
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
		assertValid(awkStatementParser,"print $0");
	}

	@Test
	public void awkStatementValidTest2() {
		assertValid(awkStatementParser,"print $1");
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
		assertValid(awkActionParser,"{ }");
	}
	
	@Test
	public void awkActionValidTest2() {
		assertValid(awkActionParser,"{ print $1 }");
	}

	@Test
	public void awkActionValidTest3() {
		assertValid(awkActionParser,"{print $1}");
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
		assertValid(awkRegularExpressionPatternParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
	}

	@Test
	public void awkRegularExpressionPatternValidTest2() {
		assertValid(awkRegularExpressionPatternParser,"//");
	}

	@Test
	public void awkRegularExpressionPatternValidTest3() {
		assertValid(awkRegularExpressionPatternParser,"/a/");
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
		assertValid(awkPatternParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
	}

	@Test
	public void awkPatternValidTest2() {
		assertValid(awkPatternParser,"//");
	}

	@Test
	public void awkPatternValidTest3() {
		assertValid(awkPatternParser,"/a/");
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
		assertValid(awkRuleParser,"/[a-zA-Z][a-zA-Z0-9_]+/ { print $1 }");
	}

	@Test
	public void awkRuleValidTest2() {
		assertValid(awkRuleParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
	}

	@Test
	public void awkRuleValidTest3() {
		assertValid(awkRuleParser,"{ print $1 }");
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
		assertValid(awkProgramParser,"// { print $1 }");
	}

	@Test
	public void awkProgramValidTest2() {
		assertValid(awkProgramParser,"/[a-zA-Z][a-zA-Z0-9_]+/");
	}

	@Test
	public void awkProgramValidTest3() {
		assertValid(awkProgramParser,"{ print $1 }");
	}

	@Test
	public void awkProgramValidTest4() {
		assertValid(awkProgramParser,"/aaaa/ \n /aa/");
	}

	@Test
	public void awkProgramValidTest5() {
		assertValid(awkProgramParser,"{ print $1 } \n { print $1 }");
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
