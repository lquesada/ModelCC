/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.test;

import java.util.Collection;

import org.modelcc.parser.Parser;

/**
 * Assert class.
 * @author elezeta
 * @serial
 */
public class ModelAssert {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private ModelAssert() { }

    public static void assertValid(Parser parser,String string) {
    	boolean exception = false;
    	try {
    		parser.parse(string);
    	} catch (Exception e) {
    		exception = true;
    	}
		if (exception==true)
			fail("Valid input expected but was invalid.");
    }

    public static void assertInvalid(Parser parser,String string) {
    	boolean exception = false;
    	try {
    		parser.parse(string);
    	} catch (Exception e) {
    		exception = true;
    	}
		if (exception==false)
			fail("Invalid input expected but was valid.");
    }

    public static void assertAmbiguityFree(Parser parser,String string) {
    	assertInterpretations(1,parser,string);
    }

    public static void assertInterpretations(int n,Parser parser,String string) {
    	boolean exception = false;
    	Collection<Object> ret = null;
    	try {
    		ret = parser.parseAll(string);
    	} catch (Exception e) {
    		exception = true;
    	}
    	if (n == 0) {
    		if (exception==false)
    			fail("0 expected but was "+n+".");
    	}
    	else {
    		if (exception==true)
    			fail(n+" expected but was 0.");
    		if (ret.size()!=n)
    			fail(n+" expected but was "+ret.size()+".");
    	}
    }

    static private void fail(String message) {
        if (message == null) {
            throw new AssertionError();
        }
        throw new AssertionError(message);
    }
}
