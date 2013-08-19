/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Test;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;

import test.languages.arithmeticcalculator.Expression;

import static org.junit.Assert.*;
import static org.modelcc.Assert.*;

/**
 *
 *
 * @author elezeta
 */
public class AssertTest {

    @Test
    public void AssertTest1() {

        try {

            Model model = JavaModelReader.read(Expression.class);

            assertValid(model,"1+2");
            
        } catch (Exception ex) {
            Logger.getLogger(AssertTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

    }
      
}




