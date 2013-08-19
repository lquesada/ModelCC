/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.util.Collection;

import org.modelcc.parser.Parser;

/**
 * Assert class.
 * @author elezeta
 * @serial
 */
public class Assert {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private Assert() { }

    public static void assertValid(Parser parser,String string) {
    	boolean exception = false;
    	try {
    		parser.parse(string);
    	} catch (Exception e) {
    		exception = true;
    	}
		if (exception==true)
			fail("Expected valid input, input was invalid.");
    }

    public static void assertInvalid(Parser parser,String string) {
    	boolean exception = false;
    	try {
    		parser.parse(string);
    	} catch (Exception e) {
    		exception = true;
    	}
		if (exception==false)
			fail("Expected invalid input, input was valid.");
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
    			fail("Expected 0 interpretations, "+n+" were found.");
    	}
    	else {
    		if (exception==true)
    			fail("Expected "+n+" interpretations, 0 were found.");
    		if (ret.size()!=n)
    			fail("Expected "+n+" interpretations, "+ret.size()+" were found.");
    	}
    }

    static private void fail(String message) {
        if (message == null) {
            throw new AssertionError();
        }
        throw new AssertionError(message);
    }
}
