/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.language.syntax;

import org.modelcc.language.syntax.RuleElementPosition;
import org.modelcc.language.syntax.GrammarFactory;
import org.modelcc.language.syntax.NullRuleElementException;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.Rule;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import test.org.modelcc.io.Serialization;
import static org.junit.Assert.*;
/**
 *
 * @author elezeta
 */
public class GrammarFactoryTest {


    public GrammarFactoryTest() {
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
    public void BuildCheck() {

        Rule r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15;

        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Statement"),re);
        re.add(new RuleElementPosition("InputStatement","1"));

        //Statement ::= OutputStatement
        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Statement"),re);
        re.add(new RuleElementPosition("OutputStatement","1"));

        //InputStatement ::= input LeftParenthesis Identifier RightParenthesis Semicolon
        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("InputStatement"),re);
        re.add(new RuleElementPosition("input","1"));
        re.add(new RuleElementPosition("LeftParenthesis","2"));
        re.add(new RuleElementPosition("Identifier","3"));
        re.add(new RuleElementPosition("RightParenthesis","4"));
        re.add(new RuleElementPosition("Semicolon","5"));

        //OutputStatement ::= output LeftParenthesis Expression RightParenthesis Semicolon
        re = new ArrayList<RuleElement>();
        r4 = new Rule(new RuleElement("OutputStatement"),re);
        re.add(new RuleElementPosition("output","1"));
        re.add(new RuleElementPosition("LeftParenthesis","2"));
        re.add(new RuleElementPosition("Expression","3"));
        re.add(new RuleElementPosition("RightParenthesis","4"));
        re.add(new RuleElementPosition("Semicolon","5"));

        //Expression ::= Expression Operator1 Expression
        re = new ArrayList<RuleElement>();
        r5 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator1","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= Expression Operator2 Expression
        re = new ArrayList<RuleElement>();
        r6 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator2","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= LeftParenthesis Expression RightParenthesis
        re = new ArrayList<RuleElement>();
        r7 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("LeftParenthesis","1"));
        re.add(new RuleElementPosition("Expression","2"));
        re.add(new RuleElementPosition("RightParenthesis","3"));

        //Expression ::= Operator1 Expression
        re = new ArrayList<RuleElement>();
        r8 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Operator1","1"));
        re.add(new RuleElementPosition("Expression","2"));

        //Expression ::= Integer
        re = new ArrayList<RuleElement>();
        r9 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Integer","1"));

        //Expression ::= Real
        re = new ArrayList<RuleElement>();
        r10 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Real","1"));

        //Start ::= main LeftBracket StatementList RightBracket
        re = new ArrayList<RuleElement>();
        r11 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("main","1"));
        re.add(new RuleElementPosition("LeftBracket","2"));
        re.add(new RuleElementPosition("StatementList","3"));
        re.add(new RuleElementPosition("RightBracket","4"));

        //StatementList ::=
        re = new ArrayList<RuleElement>();
        r12 = new Rule(new RuleElement("StatementList"),re);

        //StatementList ::= StatementListAny
        re = new ArrayList<RuleElement>();
        r13 = new Rule(new RuleElement("StatementList"),re);
        re.add(new RuleElementPosition("StatementListAny","1"));

        //StatementListAny ::= Statement
        re = new ArrayList<RuleElement>();
        r14 = new Rule(new RuleElement("StatementListAny"),re);
        re.add(new RuleElementPosition("Statement","1"));

        //StatementListAny ::= Statement StatementListAny
        re = new ArrayList<RuleElement>();
        r15 = new Rule(new RuleElement("StatementListAny"),re);
        re.add(new RuleElementPosition("Statement","1"));
        re.add(new RuleElementPosition("StatementListAny","2"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.addRule(r4);
        gf.addRule(r5);
        gf.addRule(r6);
        gf.addRule(r7);
        gf.addRule(r8);
        gf.addRule(r9);
        gf.addRule(r10);
        gf.addRule(r11);
        gf.addRule(r12);
        gf.addRule(r13);
        gf.addRule(r14);
        gf.addRule(r15);
        gf.setStartType("Start");

        try {
            gf = (GrammarFactory)Serialization.testSerialize(gf);
        } catch (ClassNotFoundException ex) {
            assertTrue(false);
            Logger.getLogger(GrammarFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(GrammarFactoryTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
    }

    @Test
    public void NullLeftElementCheck() {

        Rule r1,r2;

        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(null,re);
        re.add(new RuleElementPosition("Test","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Test","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");


        try {
            gf.create();
        } catch (NullRuleElementException ex) {
            assertFalse(false);
            return;
        }
        assertFalse(true);
    }

    @Test
    public void NullRightElementCheck() {

        Rule r1;

        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Test"),re);
        re.add(null);

        gf.addRule(r1);
        gf.setStartType("Start");


        try {
            gf.create();
        } catch (NullRuleElementException ex) {
            assertFalse(false);
            return;
        }
        assertFalse(true);
    }


}
