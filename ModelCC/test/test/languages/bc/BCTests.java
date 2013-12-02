/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.bc;

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

public class BCTests {

	static Parser<StringLiteral> stringLiteralParser;
	static Parser<ProcedureCallStatement> procedureCallStatementParser;
	static Parser<Procedure> procedureParser;

	//tests
	//ArrayType
	//AssignmentStatement
	//Block
	//Expression
	//ExpressionGroup
	//Identifier
	//IfStatement
	//InputStatement
	//IntegerLiteral
	//OutputStatement
	//Program
	//Range
	//RepeatStatement
	//SimpleType
	//Statement
	//StringLiteral
	//Type
	//Variable
	//WhileStatement

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
		stringLiteralParser = generateParser(StringLiteral.class);
		procedureCallStatementParser = generateParser(ProcedureCallStatement.class);
		procedureParser = generateParser(Procedure.class);
	}
	
	@Test
	public void stringLiteralValidTest1() {
		assertValid(stringLiteralParser,"''");
	}

	@Test
	public void stringLiteralValidTest2() {
		assertValid(stringLiteralParser,"'afjoa\" \n2'");
	}

	@Test
	public void stringLiteralValidTest3() {
		assertValid(stringLiteralParser,"'af''joa\" \n2'");
	}

	@Test
	public void stringLiteralInvalidTest1() {
		assertInvalid(stringLiteralParser,"' ' '");
	}

	@Test
	public void stringLiteralInvalidTest2() {
		assertInvalid(stringLiteralParser,"' a");
	}

	@Test
	public void procedureCallStatementValidTest1() {
		assertValid(procedureCallStatementParser,"id(1)");
	}

	@Test
	public void procedureCallStatementValidTest2() {
		assertValid(procedureCallStatementParser,"id()");
	}

	@Test
	public void procedureCallStatementValidTest3() {
		assertValid(procedureCallStatementParser,"id(a)");
	}

	@Test
	public void procedureCallStatementInvalidTest1() {
		assertInvalid(procedureCallStatementParser,"id)");
	}

	@Test
	public void procedureCallStatementInvValidTest2() {
		assertInvalid(procedureCallStatementParser,"id(");
	}

	@Test
	public void procedureValidTest1() {
		assertValid(procedureParser,"procedimiento id(a:int;b:int); inicio fin");
	}

	@Test
	public void procedureValidTest2() {
		assertValid(procedureParser,"procedimiento id(a:int;b:int); inicio a := 2; fin");
	}

	@Test
	public void procedureInvalidTest1() {
		assertInvalid(procedureParser,"procedimiento id(a:int;b:int); inicio a := 2 fin");
	}
	
	@Test
	public void procedureInvalidTest2() {
		assertInvalid(procedureParser,"procedimiento id(a:int;b:int) inicio a := 2; fin");
	}

}
