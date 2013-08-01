/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.parser.fence;

import org.modelcc.lexer.lamb.adapter.LambLexer;
import org.modelcc.lexer.Lexer;
import org.modelcc.language.syntax.RuleElementPosition;
import java.io.StringReader;
import org.modelcc.parser.fence.FenceGrammarParser;
import org.modelcc.language.lexis.TokenSpecificationCyclicPrecedenceException;
import org.modelcc.language.syntax.NullRuleElementException;
import org.modelcc.language.syntax.Grammar;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.GrammarFactory;
import org.modelcc.language.syntax.Rule;
import org.modelcc.parser.fence.ParsedSymbol;
import org.modelcc.parser.fence.ParsedGraph;
import org.modelcc.lexer.lamb.Lamb;
import org.modelcc.lexer.lamb.LexicalGraph;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import org.modelcc.language.lexis.LexicalSpecificationFactory;
import org.modelcc.language.lexis.TokenOption;
import java.util.logging.Logger;
import java.util.logging.Level;
import org.modelcc.language.lexis.LexicalSpecification;
import org.modelcc.language.lexis.TokenSpecification;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class FenceGrammarParserTest {

    public FenceGrammarParserTest() {
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

    public static ParsedSymbol searchSymbol(ParsedGraph sg,int start,int end,Object type) {
        ParsedSymbol s;
        Iterator<ParsedSymbol> ite;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            if (s.getStartIndex()==start && s.getEndIndex()==end && s.getType()==type)
                return s;
        }
        return null;
    }

    public static int countSymbols(ParsedGraph sg,int start,int end,Object type) {
        ParsedSymbol s;
        Iterator<ParsedSymbol> ite;
        int count = 0;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            if (s.getStartIndex()==start && s.getEndIndex()==end && s.getType()==type)
                count++;
        }
        return count;
    }

    @Test public void AnalysisTest1() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "main main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("main","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Start","1"));
        re.add(new RuleElementPosition("main","2"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(5,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));
        assertNotNull(searchSymbol(pg,5,8,"main"));
        assertNotNull(searchSymbol(pg,0,3,"Start"));
        assertNotNull(searchSymbol(pg,5,8,"Start"));
        assertNotNull(searchSymbol(pg,0,8,"Start"));
        assertTrue(pg.getFollowing().get(searchSymbol(pg,0,3,"main")).contains(searchSymbol(pg,5,8,"Start")));
        assertTrue(pg.getFollowing().get(searchSymbol(pg,0,3,"main")).contains(searchSymbol(pg,5,8,"main")));
        assertTrue(pg.getPreceding().get(searchSymbol(pg,5,8,"Start")).contains(searchSymbol(pg,0,3,"main")));
        assertTrue(pg.getPreceding().get(searchSymbol(pg,5,8,"main")).contains(searchSymbol(pg,0,3,"main")));
        assertTrue(pg.getPreceding().get(searchSymbol(pg,5,8,"main")).contains(searchSymbol(pg,0,3,"Start")));
        assertTrue(pg.getPreceding().get(searchSymbol(pg,5,8,"main")).contains(searchSymbol(pg,0,3,"main")));
        assertTrue(pg.getFollowing().get(searchSymbol(pg,0,3,"Start")).contains(searchSymbol(pg,5,8,"Start")));
        assertTrue(pg.getFollowing().get(searchSymbol(pg,0,3,"main")).contains(searchSymbol(pg,5,8,"main")));
        assertNull(pg.getFollowing().get(searchSymbol(pg,5,8,"main")));
        assertNull(pg.getFollowing().get(searchSymbol(pg,5,8,"Start")));
        assertNull(pg.getFollowing().get(searchSymbol(pg,0,8,"Start")));
        assertNull(pg.getPreceding().get(searchSymbol(pg,0,3,"main")));
        assertNull(pg.getPreceding().get(searchSymbol(pg,0,3,"Start")));
        assertNull(pg.getPreceding().get(searchSymbol(pg,0,8,"Start")));

    }

    @Test public void AnalysisTest2() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15;
        String input = "main { input(a); input(b); input(cd); output(5+1+(2*5)+3*5+3); }";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Point",new RegExpPatternRecognizer("\\."),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Identifier",new RegExpPatternRecognizer("[a-z]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m9 = new TokenSpecification("Operator2",new RegExpPatternRecognizer("\\*|\\/"),TokenOption.CONSIDER,null);
        m10 = new TokenSpecification("Slash",new RegExpPatternRecognizer("\\/"),TokenOption.CONSIDER,null);
        m11 = new TokenSpecification("Colon",new RegExpPatternRecognizer(":"),TokenOption.CONSIDER,null);
        m12 = new TokenSpecification("Semicolon",new RegExpPatternRecognizer(";"),TokenOption.CONSIDER,null);
        m13 = new TokenSpecification("LeftParenthesis",new RegExpPatternRecognizer("\\("),TokenOption.CONSIDER,null);
        m14 = new TokenSpecification("RightParenthesis",new RegExpPatternRecognizer("\\)"),TokenOption.CONSIDER,null);
        m15 = new TokenSpecification("LeftBracket",new RegExpPatternRecognizer("\\{"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("RightBracket",new RegExpPatternRecognizer("\\}"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m19 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);
        lsf.addTokenSpecification(m9);
        lsf.addTokenSpecification(m10);
        lsf.addTokenSpecification(m11);
        lsf.addTokenSpecification(m12);
        lsf.addTokenSpecification(m13);
        lsf.addTokenSpecification(m14);
        lsf.addTokenSpecification(m15);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);
        lsf.addTokenSpecification(m19);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



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

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        //assertEquals(1,pg.getStart().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));
        assertNotNull(searchSymbol(pg,5,5,"LeftBracket"));
        assertNotNull(searchSymbol(pg,7,11,"input"));
        assertNotNull(searchSymbol(pg,12,12,"LeftParenthesis"));
        assertNotNull(searchSymbol(pg,13,13,"Identifier"));
        assertNotNull(searchSymbol(pg,14,14,"RightParenthesis"));
        assertNotNull(searchSymbol(pg,15,15,"Semicolon"));
        assertNotNull(searchSymbol(pg,17,21,"input"));
        assertNotNull(searchSymbol(pg,22,22,"LeftParenthesis"));
        assertNotNull(searchSymbol(pg,23,23,"Identifier"));
        assertNotNull(searchSymbol(pg,24,24,"RightParenthesis"));
        assertNotNull(searchSymbol(pg,25,25,"Semicolon"));
        assertNotNull(searchSymbol(pg,27,31,"input"));
        assertNotNull(searchSymbol(pg,32,32,"LeftParenthesis"));
        assertNotNull(searchSymbol(pg,33,34,"Identifier"));
        assertNotNull(searchSymbol(pg,35,35,"RightParenthesis"));
        assertNotNull(searchSymbol(pg,36,36,"Semicolon"));
        assertNotNull(searchSymbol(pg,38,43,"output"));
        assertNotNull(searchSymbol(pg,44,44,"LeftParenthesis"));
        assertNotNull(searchSymbol(pg,45,45,"Integer"));
        assertNotNull(searchSymbol(pg,46,46,"Operator1"));
        assertNotNull(searchSymbol(pg,47,47,"Integer"));
        assertNotNull(searchSymbol(pg,48,48,"Operator1"));
        assertNotNull(searchSymbol(pg,49,49,"LeftParenthesis"));
        assertNotNull(searchSymbol(pg,50,50,"Integer"));
        assertNotNull(searchSymbol(pg,51,51,"Operator2"));
        assertNotNull(searchSymbol(pg,52,52,"Integer"));
        assertNotNull(searchSymbol(pg,53,53,"RightParenthesis"));
        assertNotNull(searchSymbol(pg,54,54,"Operator1"));
        assertNotNull(searchSymbol(pg,55,55,"Integer"));
        assertNotNull(searchSymbol(pg,56,56,"Operator2"));
        assertNotNull(searchSymbol(pg,57,57,"Integer"));
        assertNotNull(searchSymbol(pg,58,58,"Operator1"));
        assertNotNull(searchSymbol(pg,59,59,"Integer"));
        assertNotNull(searchSymbol(pg,60,60,"RightParenthesis"));
        assertNotNull(searchSymbol(pg,61,61,"Semicolon"));
        assertNotNull(searchSymbol(pg,63,63,"RightBracket"));

        assertNotNull(searchSymbol(pg,45,45,"Expression"));
        assertNotNull(searchSymbol(pg,47,47,"Expression"));
        assertNotNull(searchSymbol(pg,50,50,"Expression"));
        assertNotNull(searchSymbol(pg,52,52,"Expression"));
        assertNotNull(searchSymbol(pg,55,55,"Expression"));
        assertNotNull(searchSymbol(pg,57,57,"Expression"));
        assertNotNull(searchSymbol(pg,59,59,"Expression"));
        assertNotNull(searchSymbol(pg,45,47,"Expression"));
        assertNotNull(searchSymbol(pg,50,52,"Expression"));
        assertNotNull(searchSymbol(pg,49,53,"Expression"));
        assertNotNull(searchSymbol(pg,55,57,"Expression"));
        assertNotNull(searchSymbol(pg,57,59,"Expression"));
        assertNotNull(searchSymbol(pg,47,53,"Expression"));
        assertNotNull(searchSymbol(pg,49,55,"Expression"));
        assertNotNull(searchSymbol(pg,55,59,"Expression"));
        assertNotNull(searchSymbol(pg,45,53,"Expression"));
        assertNotNull(searchSymbol(pg,47,55,"Expression"));
        assertNotNull(searchSymbol(pg,49,57,"Expression"));
        assertNotNull(searchSymbol(pg,45,55,"Expression"));
        assertNotNull(searchSymbol(pg,47,57,"Expression"));
        assertNotNull(searchSymbol(pg,49,59,"Expression"));
        assertNotNull(searchSymbol(pg,45,57,"Expression"));
        assertNotNull(searchSymbol(pg,47,59,"Expression"));
        assertNotNull(searchSymbol(pg,45,59,"Expression"));

        assertNotNull(searchSymbol(pg,7,15,"InputStatement"));
        assertNotNull(searchSymbol(pg,17,25,"InputStatement"));
        assertNotNull(searchSymbol(pg,27,36,"InputStatement"));
        assertNotNull(searchSymbol(pg,38,61,"OutputStatement"));

        assertNotNull(searchSymbol(pg,7,15,"Statement"));
        assertNotNull(searchSymbol(pg,17,25,"Statement"));
        assertNotNull(searchSymbol(pg,27,36,"Statement"));
        assertNotNull(searchSymbol(pg,38,61,"Statement"));

        assertNotNull(searchSymbol(pg,7,15,"StatementListAny"));
        assertNotNull(searchSymbol(pg,17,25,"StatementListAny"));
        assertNotNull(searchSymbol(pg,27,36,"StatementListAny"));
        assertNotNull(searchSymbol(pg,38,61,"StatementListAny"));

        assertNotNull(searchSymbol(pg,7,25,"StatementListAny"));
        assertNotNull(searchSymbol(pg,17,36,"StatementListAny"));
        assertNotNull(searchSymbol(pg,27,61,"StatementListAny"));

        assertNotNull(searchSymbol(pg,7,36,"StatementListAny"));
        assertNotNull(searchSymbol(pg,17,61,"StatementListAny"));

        assertNotNull(searchSymbol(pg,7,61,"StatementListAny"));

        assertNotNull(searchSymbol(pg,0,63,"Start"));
    }

    @Test public void AnalysisTest3() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15;
        String input = "main { input(a); input(b); input(cd); output(5); }";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Point",new RegExpPatternRecognizer("\\."),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Identifier",new RegExpPatternRecognizer("[a-z]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m9 = new TokenSpecification("Operator2",new RegExpPatternRecognizer("\\*|\\/"),TokenOption.CONSIDER,null);
        m10 = new TokenSpecification("Slash",new RegExpPatternRecognizer("\\/"),TokenOption.CONSIDER,null);
        m11 = new TokenSpecification("Colon",new RegExpPatternRecognizer(":"),TokenOption.CONSIDER,null);
        m12 = new TokenSpecification("Semicolon",new RegExpPatternRecognizer(";"),TokenOption.CONSIDER,null);
        m13 = new TokenSpecification("LeftParenthesis",new RegExpPatternRecognizer("\\("),TokenOption.CONSIDER,null);
        m14 = new TokenSpecification("RightParenthesis",new RegExpPatternRecognizer("\\)"),TokenOption.CONSIDER,null);
        m15 = new TokenSpecification("LeftBracket",new RegExpPatternRecognizer("\\{"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("RightBracket",new RegExpPatternRecognizer("\\}"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m19 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);
        lsf.addTokenSpecification(m9);
        lsf.addTokenSpecification(m10);
        lsf.addTokenSpecification(m11);
        lsf.addTokenSpecification(m12);
        lsf.addTokenSpecification(m13);
        lsf.addTokenSpecification(m14);
        lsf.addTokenSpecification(m15);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);
        lsf.addTokenSpecification(m19);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



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

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(1,pg.getStart().size());
        assertEquals(59,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));
        assertNotNull(searchSymbol(pg,0,3,"Identifier"));
        assertNotNull(searchSymbol(pg,5,5,"LeftBracket"));
        assertNotNull(searchSymbol(pg,7,11,"input"));
        assertNotNull(searchSymbol(pg,7,11,"Identifier"));
        assertNotNull(searchSymbol(pg,12,12,"LeftParenthesis"));
        assertNotNull(searchSymbol(pg,13,13,"Identifier"));
        assertNotNull(searchSymbol(pg,14,14,"RightParenthesis"));
        assertNotNull(searchSymbol(pg,15,15,"Semicolon"));
        assertNotNull(searchSymbol(pg,17,21,"input"));
        assertNotNull(searchSymbol(pg,17,21,"Identifier"));
        assertNotNull(searchSymbol(pg,22,22,"LeftParenthesis"));
        assertNotNull(searchSymbol(pg,23,23,"Identifier"));
        assertNotNull(searchSymbol(pg,24,24,"RightParenthesis"));
        assertNotNull(searchSymbol(pg,25,25,"Semicolon"));
        assertNotNull(searchSymbol(pg,27,31,"input"));
        assertNotNull(searchSymbol(pg,27,31,"Identifier"));
        assertNotNull(searchSymbol(pg,32,32,"LeftParenthesis"));
        assertNotNull(searchSymbol(pg,33,34,"Identifier"));
        assertNotNull(searchSymbol(pg,35,35,"RightParenthesis"));
        assertNotNull(searchSymbol(pg,36,36,"Semicolon"));
        assertNotNull(searchSymbol(pg,38,43,"output"));
        assertNotNull(searchSymbol(pg,38,43,"Identifier"));
        assertNotNull(searchSymbol(pg,44,44,"LeftParenthesis"));
        assertNotNull(searchSymbol(pg,45,45,"Integer"));
        assertNotNull(searchSymbol(pg,46,46,"RightParenthesis"));
        assertNotNull(searchSymbol(pg,47,47,"Semicolon"));
        assertNotNull(searchSymbol(pg,49,49,"RightBracket"));

        assertNotNull(searchSymbol(pg,45,45,"Expression"));
        assertNotNull(searchSymbol(pg,44,46,"Expression"));


        assertNotNull(searchSymbol(pg,7,15,"InputStatement"));
        assertNotNull(searchSymbol(pg,17,25,"InputStatement"));
        assertNotNull(searchSymbol(pg,27,36,"InputStatement"));
        assertNotNull(searchSymbol(pg,38,47,"OutputStatement"));

        assertNotNull(searchSymbol(pg,7,15,"Statement"));
        assertNotNull(searchSymbol(pg,17,25,"Statement"));
        assertNotNull(searchSymbol(pg,27,36,"Statement"));
        assertNotNull(searchSymbol(pg,38,47,"Statement"));

        assertNotNull(searchSymbol(pg,7,15,"StatementListAny"));
        assertNotNull(searchSymbol(pg,17,25,"StatementListAny"));
        assertNotNull(searchSymbol(pg,27,36,"StatementListAny"));
        assertNotNull(searchSymbol(pg,38,47,"StatementListAny"));

        assertNotNull(searchSymbol(pg,7,25,"StatementListAny"));
        assertNotNull(searchSymbol(pg,17,36,"StatementListAny"));
        assertNotNull(searchSymbol(pg,27,47,"StatementListAny"));

        assertNotNull(searchSymbol(pg,7,36,"StatementListAny"));
        assertNotNull(searchSymbol(pg,17,47,"StatementListAny"));

        assertNotNull(searchSymbol(pg,7,47,"StatementListAny"));

        assertNotNull(searchSymbol(pg,7,15,"StatementList"));
        assertNotNull(searchSymbol(pg,17,25,"StatementList"));
        assertNotNull(searchSymbol(pg,27,36,"StatementList"));
        assertNotNull(searchSymbol(pg,38,47,"StatementList"));

        assertNotNull(searchSymbol(pg,7,25,"StatementList"));
        assertNotNull(searchSymbol(pg,17,36,"StatementList"));
        assertNotNull(searchSymbol(pg,27,47,"StatementList"));

        assertNotNull(searchSymbol(pg,7,36,"StatementList"));
        assertNotNull(searchSymbol(pg,17,47,"StatementList"));

        assertNotNull(searchSymbol(pg,7,47,"StatementList"));

        assertNotNull(searchSymbol(pg,0,49,"Start"));
        
    }

    @Test public void AnalysisTest4() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6;
        String input = "5+1+2";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Point",new RegExpPatternRecognizer("\\."),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Identifier",new RegExpPatternRecognizer("[a-z]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m9 = new TokenSpecification("Operator2",new RegExpPatternRecognizer("\\*|\\/"),TokenOption.CONSIDER,null);
        m10 = new TokenSpecification("Slash",new RegExpPatternRecognizer("\\/"),TokenOption.CONSIDER,null);
        m11 = new TokenSpecification("Colon",new RegExpPatternRecognizer(":"),TokenOption.CONSIDER,null);
        m12 = new TokenSpecification("Semicolon",new RegExpPatternRecognizer(";"),TokenOption.CONSIDER,null);
        m13 = new TokenSpecification("LeftParenthesis",new RegExpPatternRecognizer("\\("),TokenOption.CONSIDER,null);
        m14 = new TokenSpecification("RightParenthesis",new RegExpPatternRecognizer("\\)"),TokenOption.CONSIDER,null);
        m15 = new TokenSpecification("LeftBracket",new RegExpPatternRecognizer("\\{"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("RightBracket",new RegExpPatternRecognizer("\\}"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m19 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);
        lsf.addTokenSpecification(m9);
        lsf.addTokenSpecification(m10);
        lsf.addTokenSpecification(m11);
        lsf.addTokenSpecification(m12);
        lsf.addTokenSpecification(m13);
        lsf.addTokenSpecification(m14);
        lsf.addTokenSpecification(m15);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);
        lsf.addTokenSpecification(m19);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Expression ::= Expression Operator1 Expression
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator1","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= Expression Operator2 Expression
        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator2","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= LeftParenthesis Expression RightParenthesis
        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("LeftParenthesis","1"));
        re.add(new RuleElementPosition("Expression","2"));
        re.add(new RuleElementPosition("RightParenthesis","3"));

        //Expression ::= Operator1 Expression
        re = new ArrayList<RuleElement>();
        r4 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Operator1","1"));
        re.add(new RuleElementPosition("Expression","2"));

        //Expression ::= Integer
        re = new ArrayList<RuleElement>();
        r5 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Integer","1"));

        //Expression ::= Real
        re = new ArrayList<RuleElement>();
        r6 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Real","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.addRule(r4);
        gf.addRule(r5);
        gf.addRule(r6);
        gf.setStartType("Expression");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        /*
        ParsedSymbol s;
        Iterator<ParsedSymbol> ite;
        for (ite = pg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            System.out.println(s.getType()+" "+s.getStartIndex()+" "+s.getEndIndex()+" ");
        }*/

        assertEquals(1,pg.getStart().size());
        assertEquals(16,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,0,"Integer"));
        assertNotNull(searchSymbol(pg,1,1,"Operator1"));
        assertNotNull(searchSymbol(pg,2,2,"Integer"));
        assertNotNull(searchSymbol(pg,1,2,"Integer"));
        assertNotNull(searchSymbol(pg,3,3,"Operator1"));
        assertNotNull(searchSymbol(pg,4,4,"Integer"));
        assertNotNull(searchSymbol(pg,3,4,"Integer"));
        assertNotNull(searchSymbol(pg,0,0,"Expression"));
        assertNotNull(searchSymbol(pg,1,2,"Expression"));
        assertNotNull(searchSymbol(pg,2,2,"Expression"));
        assertNotNull(searchSymbol(pg,3,4,"Expression"));
        assertNotNull(searchSymbol(pg,1,4,"Expression"));
        assertNotNull(searchSymbol(pg,4,4,"Expression"));
        assertNotNull(searchSymbol(pg,0,2,"Expression"));
        assertNotNull(searchSymbol(pg,2,4,"Expression"));
        assertNotNull(searchSymbol(pg,4,4,"Expression"));
    }

    @Test public void AnalysisTest5() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6;
        String input = "5+1+2+1";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m6 = new TokenSpecification("Point",new RegExpPatternRecognizer("\\."),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Identifier",new RegExpPatternRecognizer("[a-z]+"),TokenOption.CONSIDER,null);
        m8 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m9 = new TokenSpecification("Operator2",new RegExpPatternRecognizer("\\*|\\/"),TokenOption.CONSIDER,null);
        m10 = new TokenSpecification("Slash",new RegExpPatternRecognizer("\\/"),TokenOption.CONSIDER,null);
        m11 = new TokenSpecification("Colon",new RegExpPatternRecognizer(":"),TokenOption.CONSIDER,null);
        m12 = new TokenSpecification("Semicolon",new RegExpPatternRecognizer(";"),TokenOption.CONSIDER,null);
        m13 = new TokenSpecification("LeftParenthesis",new RegExpPatternRecognizer("\\("),TokenOption.CONSIDER,null);
        m14 = new TokenSpecification("RightParenthesis",new RegExpPatternRecognizer("\\)"),TokenOption.CONSIDER,null);
        m15 = new TokenSpecification("LeftBracket",new RegExpPatternRecognizer("\\{"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("RightBracket",new RegExpPatternRecognizer("\\}"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m19 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m6);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m8);
        lsf.addTokenSpecification(m9);
        lsf.addTokenSpecification(m10);
        lsf.addTokenSpecification(m11);
        lsf.addTokenSpecification(m12);
        lsf.addTokenSpecification(m13);
        lsf.addTokenSpecification(m14);
        lsf.addTokenSpecification(m15);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);
        lsf.addTokenSpecification(m19);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Expression ::= Expression Operator1 Expression
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator1","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= Expression Operator2 Expression
        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator2","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= LeftParenthesis Expression RightParenthesis
        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("LeftParenthesis","1"));
        re.add(new RuleElementPosition("Expression","2"));
        re.add(new RuleElementPosition("RightParenthesis","3"));

        //Expression ::= Operator1 Expression
        re = new ArrayList<RuleElement>();
        r4 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Operator1","1"));
        re.add(new RuleElementPosition("Expression","2"));

        //Expression ::= Integer
        re = new ArrayList<RuleElement>();
        r5 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Integer","1"));

        //Expression ::= Real
        re = new ArrayList<RuleElement>();
        r6 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("Real","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.addRule(r4);
        gf.addRule(r5);
        gf.addRule(r6);
        gf.setStartType("Expression");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        /*
        ParsedSymbol s;
        Iterator<ParsedSymbol> ite;
        for (ite = pg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            System.out.println(s.getType()+" "+s.getStartIndex()+" "+s.getEndIndex()+" ");
        }*/

        assertEquals(1,pg.getStart().size());
        assertEquals(26,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,0,"Integer"));
        assertNotNull(searchSymbol(pg,1,1,"Operator1"));
        assertNotNull(searchSymbol(pg,2,2,"Integer"));
        assertNotNull(searchSymbol(pg,1,2,"Integer"));
        assertNotNull(searchSymbol(pg,3,3,"Operator1"));
        assertNotNull(searchSymbol(pg,4,4,"Integer"));
        assertNotNull(searchSymbol(pg,3,4,"Integer"));
        assertNotNull(searchSymbol(pg,5,5,"Operator1"));
        assertNotNull(searchSymbol(pg,6,6,"Integer"));
        assertNotNull(searchSymbol(pg,5,6,"Integer"));
        assertNotNull(searchSymbol(pg,0,0,"Expression"));
        assertNotNull(searchSymbol(pg,1,2,"Expression"));
        assertNotNull(searchSymbol(pg,2,2,"Expression"));
        assertNotNull(searchSymbol(pg,3,4,"Expression"));
        assertNotNull(searchSymbol(pg,1,4,"Expression"));
        assertNotNull(searchSymbol(pg,4,4,"Expression"));
        assertNotNull(searchSymbol(pg,0,2,"Expression"));
        assertNotNull(searchSymbol(pg,2,4,"Expression"));
        assertNotNull(searchSymbol(pg,4,4,"Expression"));
        assertNotNull(searchSymbol(pg,3,6,"Expression"));
        assertNotNull(searchSymbol(pg,1,6,"Expression"));
        assertNotNull(searchSymbol(pg,4,6,"Expression"));
        assertNotNull(searchSymbol(pg,2,6,"Expression"));
        assertNotNull(searchSymbol(pg,4,6,"Expression"));
        assertNotNull(searchSymbol(pg,5,6,"Expression"));
        assertNotNull(searchSymbol(pg,6,6,"Expression"));
    }

    @Test public void EmptyRuleTest1() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2,r3;
        String input = "main main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("main","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Start","1"));
        re.add(new RuleElementPosition("Separator","2"));
        re.add(new RuleElementPosition("main","3"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("Separator"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(5,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));
        assertNotNull(searchSymbol(pg,5,8,"main"));
        assertNotNull(searchSymbol(pg,0,3,"Start"));
        assertNotNull(searchSymbol(pg,5,8,"Start"));
        assertNotNull(searchSymbol(pg,0,8,"Start"));

    }

    @Test public void EmptyRuleTestMatch() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Separator","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Separator"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(1,pg.getStart().size());
    }

    @Test public void EmptyRuleTest2() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2,r3;
        String input = "main main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("main","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Start","1"));
        re.add(new RuleElementPosition("main","2"));
        re.add(new RuleElementPosition("Separator","3"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("Separator"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(5,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));
        assertNotNull(searchSymbol(pg,5,8,"main"));
        assertNotNull(searchSymbol(pg,0,3,"Start"));
        assertNotNull(searchSymbol(pg,5,8,"Start"));
        assertNotNull(searchSymbol(pg,0,8,"Start"));

    }

    @Test public void ChainedEmptyRuleTest() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2,r3,r4,r5;
        String input = "main main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("main","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Start","3"));
        re.add(new RuleElementPosition("main","6"));
        re.add(new RuleElementPosition("Separator","9"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("Separator"),re);
        re.add(new RuleElementPosition("Separator2","1"));
        re.add(new RuleElementPosition("Separator3","1"));

        re = new ArrayList<RuleElement>();
        r4 = new Rule(new RuleElement("Separator2"),re);
        re.add(new RuleElementPosition("Separator3","1"));

        re = new ArrayList<RuleElement>();
        r5 = new Rule(new RuleElement("Separator3"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.addRule(r4);
        gf.addRule(r5);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(5,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));
        assertNotNull(searchSymbol(pg,5,8,"main"));
        assertNotNull(searchSymbol(pg,0,3,"Start"));
        assertNotNull(searchSymbol(pg,5,8,"Start"));
        assertNotNull(searchSymbol(pg,0,8,"Start"));

    }

    @Test public void CyclicRuleTest1() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2,r3;
        String input = "main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Object"),re);
        re.add(new RuleElementPosition("main","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Object","1"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("Object"),re);
        re.add(new RuleElementPosition("Start","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);

        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }
        
        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(3,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));
        assertNotNull(searchSymbol(pg,0,3,"Start"));
        assertNotNull(searchSymbol(pg,0,3,"Object"));

    }

    @Test public void CyclicRuleTest2() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2,r3;
        String input = "main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("main","1"));
        re.add(new RuleElementPosition("Empty","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Start","1"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("Empty"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);

        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(2,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));
        assertNotNull(searchSymbol(pg,0,3,"Start"));

    }

    @Test public void CyclicRuleTest3() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2,r3;
        String input = "main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("main","1"));
        re.add(new RuleElementPosition("Empty","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Start","1"));
        re.add(new RuleElementPosition("Empty","1"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("Empty"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);

        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(2,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));
        assertNotNull(searchSymbol(pg,0,3,"Start"));

    }

    @Test public void SingleSymbolTest() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        String input = "main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("Operator1",new RegExpPatternRecognizer("-|\\+"),TokenOption.CONSIDER,null);
        m16 = new TokenSpecification("main",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
        m17 = new TokenSpecification("input",new RegExpPatternRecognizer("input"),TokenOption.CONSIDER,null);
        m18 = new TokenSpecification("output",new RegExpPatternRecognizer("output"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);
        lsf.addTokenSpecification(m4);
        lsf.addTokenSpecification(m5);
        lsf.addTokenSpecification(m7);
        lsf.addTokenSpecification(m16);
        lsf.addTokenSpecification(m17);
        lsf.addTokenSpecification(m18);


        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        

        //Statement ::= InputStatement

        gf.setStartType("main");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceGrammarParserTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        assertEquals(1,pg.getStart().size());
        assertEquals(1,pg.getSymbols().size());
        assertNotNull(searchSymbol(pg,0,3,"main"));

    }


        /*
        for (Iterator<Token> ite = lg.getTokens().iterator();ite.hasNext();) {
            Token tk = ite.next();
            System.out.println("Token of type "+tk.getType()+": "+tk.getText()+" ("+tk.getStart()+"-"+tk.getEnd()+")");
        }
         */


}