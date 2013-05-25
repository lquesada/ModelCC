/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.language.syntax;

import org.modelcc.language.syntax.RuleElementPosition;
import org.modelcc.language.syntax.ConstraintsFactory;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.modelcc.language.syntax.CyclicCompositionPrecedenceException;
import org.modelcc.language.syntax.Constraints;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.Rule;
import java.util.List;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.modelcc.language.syntax.CyclicSelectionPrecedenceException;

import test.org.modelcc.io.Serialization;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class ConstraintsFactoryTest {

    public ConstraintsFactoryTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void CyclicPrecedenceCheck1() {

        Rule r1,r2;

        //We define the syntactic rules:
        ConstraintsFactory cf = new ConstraintsFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("TestOne"),re);
        re.add(new RuleElementPosition("Test","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("TestTwo"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        cf.addCompositionPrecedences(r2,r1);
        cf.addCompositionPrecedences(r1,r2);
        try {
            cf = (ConstraintsFactory)Serialization.testSerialize(cf);
        } catch (ClassNotFoundException ex) {
            assertTrue(false);
            Logger.getLogger(ConstraintsFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Constraints c;
        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            assertFalse(false);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            assertTrue(false);
            return;
        }
        assertFalse(true);
    }

    @Test
    public void CyclicPrecedenceCheck2() {

        Rule r1,r2,r3;

        //We define the syntactic rules:
        ConstraintsFactory cf = new ConstraintsFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("TestOne"),re);
        re.add(new RuleElementPosition("Test","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("TestTwo"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("TestThree"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        cf.addCompositionPrecedences(r2,r3);
        cf.addCompositionPrecedences(r3,r1);
        cf.addCompositionPrecedences(r1,r2);

        Constraints c;
        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            assertFalse(false);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(GrammarFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }

        assertFalse(true);
    }

    @Test
    public void CyclicPrecedenceCheck3() {

        Rule r1,r2,r3;

        //We define the syntactic rules:
        ConstraintsFactory cf = new ConstraintsFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("TestOne"),re);
        re.add(new RuleElementPosition("Test","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("TestTwo"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("TestThree"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        cf.addCompositionPrecedences(r3,r1);
        cf.addCompositionPrecedences(r1,r2);

        Constraints c;
        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            assertTrue(false);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(GrammarFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }

        assertTrue(true);
    }


    @Test
    public void SelectionPrecedenceCheck1() {

        Rule r1,r2;

        //We define the syntactic rules:
        ConstraintsFactory cf = new ConstraintsFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("TestOne"),re);
        re.add(new RuleElementPosition("Test","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("TestTwo"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        cf.addSelectionPrecedences(r2,r1);
        cf.addSelectionPrecedences(r1,r2);

        Constraints c;
        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(GrammarFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            assertFalse(false);
            return;
        }
        assertFalse(true);
    }

    @Test
    public void SelectionPrecedenceCheck2() {

        Rule r1,r2,r3;

        //We define the syntactic rules:
        ConstraintsFactory cf = new ConstraintsFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("TestOne"),re);
        re.add(new RuleElementPosition("Test","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("TestTwo"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("TestThree"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        cf.addSelectionPrecedences(r2,r3);
        cf.addSelectionPrecedences(r3,r1);
        cf.addSelectionPrecedences(r1,r2);

        Constraints c;
        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(GrammarFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            assertFalse(false);
            return;
        }

        assertFalse(true);
    }

    @Test
    public void SelectionPrecedenceCheck3() {

        Rule r1,r2,r3;

        //We define the syntactic rules:
        ConstraintsFactory cf = new ConstraintsFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("TestOne"),re);
        re.add(new RuleElementPosition("Test","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("TestTwo"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("TestThree"),re);
        re.add(new RuleElementPosition("TestOne","1"));

        cf.addSelectionPrecedences(r3,r1);
        cf.addSelectionPrecedences(r1,r2);

        Constraints c;
        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(GrammarFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(GrammarFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            assertTrue(false);
            return;
        }

        assertTrue(true);
    }

}
