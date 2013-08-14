/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.parser.fence;

import java.util.Map;
import java.util.Set;
import org.modelcc.language.syntax.PostSymbolBuilder;
import org.modelcc.lexer.lamb.adapter.LambLexer;
import org.modelcc.lexer.Lexer;
import org.modelcc.language.syntax.RuleElementPosition;
import java.io.StringReader;
import org.modelcc.parser.fence.FenceConstraintEnforcer;
import org.modelcc.language.syntax.CyclicSelectionPrecedenceException;
import org.modelcc.language.syntax.CyclicCompositionPrecedenceException;
import org.modelcc.language.lexis.TokenSpecificationCyclicPrecedenceException;
import org.modelcc.parser.fence.Symbol;
import org.modelcc.language.syntax.Constraints;
import org.modelcc.language.syntax.ConstraintsFactory;
import org.modelcc.parser.fence.SyntaxGraph;
import org.modelcc.parser.fence.FenceGrammarParser;
import org.modelcc.language.syntax.NullRuleElementException;
import org.modelcc.language.syntax.Grammar;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.GrammarFactory;
import org.modelcc.language.syntax.Rule;
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
import org.modelcc.language.syntax.AssociativityConstraint;
import org.modelcc.language.syntax.SymbolBuilder;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class FenceConstraintEnforcerTest {

  private class CountSymbolBuilder extends SymbolBuilder {

      /**
       * Serial Version ID
       */
      private static final long serialVersionUID = 31415926535897932L;

        private int count = 0;

        @Override
        public boolean build(Symbol t,Object data) {
            count++;
            return true;
        }

        public int getCount() {
            return count;
        }

    }
    public FenceConstraintEnforcerTest() {
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

    public static Symbol searchSymbol(SyntaxGraph sg,int start,int end,Object type) {
        Symbol s;
        Iterator<Symbol> ite;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            if (s.getStartIndex()==start && s.getEndIndex()==end && s.getType()==type)
                return s;
        }
        return null;
    }

    public static Symbol searchSymbolFirstContent(SyntaxGraph sg,int start,int end,Object type,Symbol first) {
        Symbol s;
        Iterator<Symbol> ite;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            if (!s.getContents().isEmpty())
                if (s.getStartIndex()==start && s.getEndIndex()==end && s.getType()==type && s.getContents().get(0) == first)
                    return s;
        }
        return null;
    }

    public static int countSymbols(SyntaxGraph sg,int start,int end,Object type) {
        Symbol s;
        Iterator<Symbol> ite;
        int count = 0;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            if (s.getStartIndex()==start && s.getEndIndex()==end && s.getType()==type)
                count++;
        }
        return count;
    }

    /*
    private static void recShow(Symbol t, int tab) {
        int i;
        for (i = 0;i < tab;i++)
            System.out.print("    ");
        if (t == null)
            System.out.println("%null%");
        else {
            System.out.println(t.getType()+"    "+t.getStartIndex()+"-"+t.getEndIndex());
            for (i = 0;i < t.getContents().size();i++) {
                recShow(t.getContents().get(i),tab+1);
            }
        }
    }*/

    @Test public void ConstraintEnforcementTest1() {
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        /*
        Iterator<ParsedSymbol> itep;
        ParsedSymbol ps;
        for (itep = pg.getSymbols().iterator();itep.hasNext();) {
            ps = itep.next();
            System.out.println(""+ps.getType()+"   "+ps.getStartIndex()+"-"+ps.getEndIndex());
            System.out.println(pg.getPreceding().get(ps));
            System.out.println(pg.getFollowing().get(ps));
        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());

        Iterator<Symbol> ite;
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());

        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(" ");

        Symbol s;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            System.out.println(""+s+"    "+s.getType()+"   "+s.getStartIndex()+"-"+s.getEndIndex());

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());

*/

        assertEquals(4,sg.getSymbols().size());
        assertEquals(1,sg.getRoots().size());
        assertNotNull(searchSymbol(sg,0,3,"main"));
        assertNotNull(searchSymbol(sg,0,3,"Start"));
        assertNotNull(searchSymbol(sg,5,8,"main"));
        assertNull(searchSymbol(sg,5,8,"Start"));
        assertNotNull(searchSymbol(sg,0,8,"Start"));
        assertTrue(sg.getRoots().contains(searchSymbol(sg,0,8,"Start")));
    }

    @Test public void ConstraintEnforcementTest2() {
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);
/*
        Symbol s;
        Iterator<Symbol> ite;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            System.out.println(""+s+"    "+s.getType()+"   "+s.getStartIndex()+"-"+s.getEndIndex());

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
  */
        assertEquals(1,sg.getRoots().size());
        assertEquals(38,sg.getSymbols().size());
        assertNotNull(searchSymbol(sg,0,3,"main"));
        assertNotNull(searchSymbol(sg,5,5,"LeftBracket"));
        assertNotNull(searchSymbol(sg,7,11,"input"));
        assertNotNull(searchSymbol(sg,12,12,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,13,13,"Identifier"));
        assertNotNull(searchSymbol(sg,14,14,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,15,15,"Semicolon"));
        assertNotNull(searchSymbol(sg,17,21,"input"));
        assertNotNull(searchSymbol(sg,22,22,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,23,23,"Identifier"));
        assertNotNull(searchSymbol(sg,24,24,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,25,25,"Semicolon"));
        assertNotNull(searchSymbol(sg,27,31,"input"));
        assertNotNull(searchSymbol(sg,32,32,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,33,34,"Identifier"));
        assertNotNull(searchSymbol(sg,35,35,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,36,36,"Semicolon"));
        assertNotNull(searchSymbol(sg,38,43,"output"));
        assertNotNull(searchSymbol(sg,44,44,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,45,45,"Integer"));
        assertNotNull(searchSymbol(sg,46,46,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,47,47,"Semicolon"));
        assertNotNull(searchSymbol(sg,49,49,"RightBracket"));

        assertNotNull(searchSymbol(sg,45,45,"Expression"));

        assertNotNull(searchSymbol(sg,7,15,"InputStatement"));
        assertEquals(5,searchSymbol(sg,7,15,"InputStatement").getElements().size());
        assertEquals("input",searchSymbol(sg,7,15,"InputStatement").getElements().get(0).getType());
        assertEquals("LeftParenthesis",searchSymbol(sg,7,15,"InputStatement").getElements().get(1).getType());
        assertEquals("Identifier",searchSymbol(sg,7,15,"InputStatement").getElements().get(2).getType());
        assertEquals("RightParenthesis",searchSymbol(sg,7,15,"InputStatement").getElements().get(3).getType());
        assertEquals("Semicolon",searchSymbol(sg,7,15,"InputStatement").getElements().get(4).getType());
        assertNotNull(searchSymbol(sg,17,25,"InputStatement"));
        assertNotNull(searchSymbol(sg,27,36,"InputStatement"));
        assertNotNull(searchSymbol(sg,38,47,"OutputStatement"));

        assertNotNull(searchSymbol(sg,7,15,"Statement"));
        assertNotNull(searchSymbol(sg,17,25,"Statement"));
        assertNotNull(searchSymbol(sg,27,36,"Statement"));
        assertNotNull(searchSymbol(sg,38,47,"Statement"));

        assertNull(searchSymbol(sg,7,15,"StatementListAny"));
        assertNull(searchSymbol(sg,17,25,"StatementListAny"));
        assertNull(searchSymbol(sg,27,36,"StatementListAny"));
        assertNotNull(searchSymbol(sg,38,47,"StatementListAny"));

        assertNull(searchSymbol(sg,7,25,"StatementListAny"));
        assertNull(searchSymbol(sg,17,36,"StatementListAny"));
        assertNotNull(searchSymbol(sg,27,47,"StatementListAny"));

        assertNull(searchSymbol(sg,7,36,"StatementListAny"));
        assertNotNull(searchSymbol(sg,17,47,"StatementListAny"));

        assertNotNull(searchSymbol(sg,7,47,"StatementListAny"));

        assertNotNull(searchSymbol(sg,7,47,"StatementList"));

        assertNotNull(searchSymbol(sg,0,49,"Start"));
        assertEquals(4,searchSymbol(sg,0,49,"Start").getElements().size());
        assertEquals("main",searchSymbol(sg,0,49,"Start").getElements().get(0).getType());
        assertEquals("LeftBracket",searchSymbol(sg,0,49,"Start").getElements().get(1).getType());
        assertEquals("StatementList",searchSymbol(sg,0,49,"Start").getElements().get(2).getType());
        assertEquals("RightBracket",searchSymbol(sg,0,49,"Start").getElements().get(3).getType());
    }

    @Test public void ConstraintEnforcementTest3() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6;
        String input = "5";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(2,sg.getSymbols().size());
        assertEquals(1,sg.getRoots().size());

        assertNotNull(searchSymbol(sg,0,0,"Integer"));

        assertNotNull(searchSymbol(sg,0,0,"Expression"));

    }



    @Test public void AssociativityTest2() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6;
        String input = "5+3+2";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        cf.setAssociativity("Operator1",AssociativityConstraint.RIGHT_TO_LEFT);
        Constraints c;


        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(10,sg.getSymbols().size());
        assertEquals(1,sg.getRoots().size());

        assertNotNull(searchSymbol(sg,0,0,"Integer"));
        assertNotNull(searchSymbol(sg,1,1,"Operator1"));
        assertNotNull(searchSymbol(sg,2,2,"Integer"));
        assertNotNull(searchSymbol(sg,3,3,"Operator1"));
        assertNotNull(searchSymbol(sg,4,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Integer"));
        assertNull(searchSymbol(sg,3,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Expression"));

        assertNull(searchSymbol(sg,3,4,"Expression"));

        assertNotNull(searchSymbol(sg,0,0,"Expression"));
        assertNotNull(searchSymbol(sg,2,2,"Expression"));
        assertNotNull(searchSymbol(sg,4,4,"Expression"));

        assertNull(searchSymbol(sg,0,2,"Expression"));
        assertNotNull(searchSymbol(sg,2,4,"Expression"));

        assertNotNull(searchSymbol(sg,0,4,"Expression"));

        assertNull(searchSymbol(sg,1,4,"Expression"));

        assertEquals(searchSymbol(sg,1,1,"Operator1"),searchSymbolFirstContent(sg,0,4,"Expression",searchSymbol(sg,0,0,"Expression")).getContents().get(1));
        assertEquals(searchSymbol(sg,2,4,"Expression"),searchSymbolFirstContent(sg,0,4,"Expression",searchSymbol(sg,0,0,"Expression")).getContents().get(2));
    }


    @Test public void AssociativityTest3() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6;
        String input = "5+3+2";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        cf.setAssociativity("Operator1",AssociativityConstraint.NON_ASSOCIATIVE);
        Constraints c;


        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(0,sg.getRoots().size());

    }


    @Test public void PrecedenceTest1() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6;
        String input = "5+3*2";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        cf.addCompositionPrecedences(r2, r1);
        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

/*
        Iterator<ParsedSymbol> itep;
        ParsedSymbol ps;
        Iterator<ParsedSymbol> itep2;
        ParsedSymbol ps2;
        for (itep = pg.getSymbols().iterator();itep.hasNext();) {
            ps = itep.next();
            System.out.println(""+ps.getType()+"   "+ps.getStartIndex()+"-"+ps.getEndIndex());

            if (pg.getPreceding().get(ps) != null)
            for (itep2 = pg.getPreceding().get(ps).iterator();itep2.hasNext();) {
                ps2 = itep2.next();
                System.out.println("    a preceding: "+ps2.getType()+"   "+ps2.getStartIndex()+"-"+ps2.getEndIndex());
            }

            if (pg.getFollowing().get(ps) != null)
            for (itep2 = pg.getFollowing().get(ps).iterator();itep2.hasNext();) {
                ps2 = itep2.next();
                System.out.println("    a following: "+ps2.getType()+"   "+ps2.getStartIndex()+"-"+ps2.getEndIndex());
            }

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());

        Symbol s;
        Iterator<Symbol> ite;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            System.out.println(""+s+"    "+s.getType()+"   "+s.getStartIndex()+"-"+s.getEndIndex());

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
*/


        /*
        Iterator<Symbol> ite;
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
*/
        assertEquals(10,sg.getSymbols().size());
        assertEquals(1,sg.getRoots().size());

        assertNotNull(searchSymbol(sg,0,0,"Integer"));
        assertNotNull(searchSymbol(sg,1,1,"Operator1"));
        assertNotNull(searchSymbol(sg,2,2,"Integer"));
        assertNotNull(searchSymbol(sg,3,3,"Operator2"));
        assertNotNull(searchSymbol(sg,4,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Integer"));
        assertNull(searchSymbol(sg,3,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Expression"));

        assertNull(searchSymbol(sg,3,4,"Expression"));

        assertNotNull(searchSymbol(sg,0,0,"Expression"));
        assertNotNull(searchSymbol(sg,2,2,"Expression"));
        assertNotNull(searchSymbol(sg,4,4,"Expression"));

        assertNotNull(searchSymbol(sg,2,4,"Expression"));

        assertNotNull(searchSymbol(sg,0,4,"Expression"));

        assertNull(searchSymbol(sg,1,4,"Expression"));

        assertEquals(searchSymbol(sg,3,3,"Operator2"),searchSymbolFirstContent(sg,2,4,"Expression",searchSymbol(sg,2,2,"Expression")).getContents().get(1));
        assertEquals(searchSymbol(sg,4,4,"Expression"),searchSymbolFirstContent(sg,2,4,"Expression",searchSymbol(sg,2,2,"Expression")).getContents().get(2));
    }

    @Test public void PrecedenceTest2() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6;
        String input = "5+3*2";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        cf.addCompositionPrecedences(r1, r2);
        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

/*
        Iterator<ParsedSymbol> itep;
        ParsedSymbol ps;
        Iterator<ParsedSymbol> itep2;
        ParsedSymbol ps2;
        for (itep = pg.getSymbols().iterator();itep.hasNext();) {
            ps = itep.next();
            System.out.println(""+ps.getType()+"   "+ps.getStartIndex()+"-"+ps.getEndIndex());

            if (pg.getPreceding().get(ps) != null)
            for (itep2 = pg.getPreceding().get(ps).iterator();itep2.hasNext();) {
                ps2 = itep2.next();
                System.out.println("    a preceding: "+ps2.getType()+"   "+ps2.getStartIndex()+"-"+ps2.getEndIndex());
            }

            if (pg.getFollowing().get(ps) != null)
            for (itep2 = pg.getFollowing().get(ps).iterator();itep2.hasNext();) {
                ps2 = itep2.next();
                System.out.println("    a following: "+ps2.getType()+"   "+ps2.getStartIndex()+"-"+ps2.getEndIndex());
            }

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());

        Symbol s;
        Iterator<Symbol> ite;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            System.out.println(""+s+"    "+s.getType()+"   "+s.getStartIndex()+"-"+s.getEndIndex());

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
*/
/*


        Iterator<Symbol> ite;
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
*/
        assertEquals(10,sg.getSymbols().size());
        assertEquals(1,sg.getRoots().size());

        assertNotNull(searchSymbol(sg,0,0,"Integer"));
        assertNotNull(searchSymbol(sg,1,1,"Operator1"));
        assertNotNull(searchSymbol(sg,2,2,"Integer"));
        assertNotNull(searchSymbol(sg,3,3,"Operator2"));
        assertNotNull(searchSymbol(sg,4,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Integer"));
        assertNull(searchSymbol(sg,3,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Expression"));

        assertNull(searchSymbol(sg,3,4,"Expression"));

        assertNotNull(searchSymbol(sg,0,0,"Expression"));
        assertNotNull(searchSymbol(sg,2,2,"Expression"));
        assertNotNull(searchSymbol(sg,4,4,"Expression"));

        assertNotNull(searchSymbol(sg,0,2,"Expression"));

        assertNotNull(searchSymbol(sg,0,4,"Expression"));

        assertNull(searchSymbol(sg,1,4,"Expression"));

        assertEquals(searchSymbol(sg,3,3,"Operator2"),searchSymbolFirstContent(sg,0,4,"Expression",searchSymbol(sg,0,2,"Expression")).getContents().get(1));
        assertEquals(searchSymbol(sg,4,4,"Expression"),searchSymbolFirstContent(sg,0,4,"Expression",searchSymbol(sg,0,2,"Expression")).getContents().get(2));
    }

    @Test public void PrecedenceTest3() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6,r7,r8,r9;
        String input = "5+3*2";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Expression ::= Expression Operator1 Expression
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("BinaryExpression1"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator1","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= Expression Operator2 Expression
        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("BinaryExpression2"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator2","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= LeftParenthesis Expression RightParenthesis
        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("ParenthesizedExpression"),re);
        re.add(new RuleElementPosition("LeftParenthesis","1"));
        re.add(new RuleElementPosition("Expression","2"));
        re.add(new RuleElementPosition("RightParenthesis","3"));

        //Expression ::= Operator1 Expression
        re = new ArrayList<RuleElement>();
        r4 = new Rule(new RuleElement("UnaryExpression"),re);
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

        //Expression ::= BinaryExpression1
        re = new ArrayList<RuleElement>();
        r7 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("BinaryExpression1","1"));

        //Expression ::= BinaryExpression2
        re = new ArrayList<RuleElement>();
        r8 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("BinaryExpression2","1"));

        //Expression ::= ParenthesizedExpression
        re = new ArrayList<RuleElement>();
        r9 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("ParenthesizedExpression","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.addRule(r4);
        gf.addRule(r5);
        gf.addRule(r6);
        gf.addRule(r7);
        gf.addRule(r8);
        gf.addRule(r9);
        gf.setStartType("Expression");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        cf.addCompositionPrecedences(r2, r1);
        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

/*
        Iterator<ParsedSymbol> itep;
        ParsedSymbol ps;
        Iterator<ParsedSymbol> itep2;
        ParsedSymbol ps2;
        for (itep = pg.getSymbols().iterator();itep.hasNext();) {
            ps = itep.next();
            System.out.println(""+ps.getType()+"   "+ps.getStartIndex()+"-"+ps.getEndIndex());

            if (pg.getPreceding().get(ps) != null)
            for (itep2 = pg.getPreceding().get(ps).iterator();itep2.hasNext();) {
                ps2 = itep2.next();
                System.out.println("    a preceding: "+ps2.getType()+"   "+ps2.getStartIndex()+"-"+ps2.getEndIndex());
            }

            if (pg.getFollowing().get(ps) != null)
            for (itep2 = pg.getFollowing().get(ps).iterator();itep2.hasNext();) {
                ps2 = itep2.next();
                System.out.println("    a following: "+ps2.getType()+"   "+ps2.getStartIndex()+"-"+ps2.getEndIndex());
            }

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());

        Symbol s;
        Iterator<Symbol> ite;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            System.out.println(""+s+"    "+s.getType()+"   "+s.getStartIndex()+"-"+s.getEndIndex());

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
*/


/*
        Iterator<Symbol> ite;
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
*/
        assertEquals(12,sg.getSymbols().size());
        assertEquals(1,sg.getRoots().size());

        assertNotNull(searchSymbol(sg,0,0,"Integer"));
        assertNotNull(searchSymbol(sg,1,1,"Operator1"));
        assertNotNull(searchSymbol(sg,2,2,"Integer"));
        assertNotNull(searchSymbol(sg,3,3,"Operator2"));
        assertNotNull(searchSymbol(sg,4,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Integer"));
        assertNull(searchSymbol(sg,3,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Expression"));

        assertNull(searchSymbol(sg,3,4,"Expression"));

        assertNotNull(searchSymbol(sg,0,0,"Expression"));
        assertNotNull(searchSymbol(sg,2,2,"Expression"));
        assertNotNull(searchSymbol(sg,4,4,"Expression"));

        assertNotNull(searchSymbol(sg,2,4,"Expression"));
        assertNotNull(searchSymbol(sg,2,4,"BinaryExpression2"));

        assertNotNull(searchSymbol(sg,0,4,"Expression"));
        assertNotNull(searchSymbol(sg,0,4,"BinaryExpression1"));

        assertNull(searchSymbol(sg,1,4,"Expression"));

        assertEquals(searchSymbol(sg,3,3,"Operator2"),searchSymbolFirstContent(sg,2,4,"BinaryExpression2",searchSymbol(sg,2,2,"Expression")).getContents().get(1));
        assertEquals(searchSymbol(sg,4,4,"Expression"),searchSymbolFirstContent(sg,2,4,"BinaryExpression2",searchSymbol(sg,2,2,"Expression")).getContents().get(2));
    }


    @Test public void PrecedenceTest4() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6,r7,r8,r9;
        String input = "2+3*2";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Expression ::= Expression Operator1 Expression
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("BinaryExpression1"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator1","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= Expression Operator2 Expression
        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("BinaryExpression2"),re);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator2","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= LeftParenthesis Expression RightParenthesis
        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("ParenthesizedExpression"),re);
        re.add(new RuleElementPosition("LeftParenthesis","1"));
        re.add(new RuleElementPosition("Expression","2"));
        re.add(new RuleElementPosition("RightParenthesis","3"));

        //Expression ::= Operator1 Expression
        re = new ArrayList<RuleElement>();
        r4 = new Rule(new RuleElement("UnaryExpression"),re);
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

        //Expression ::= BinaryExpression1
        re = new ArrayList<RuleElement>();
        r7 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("BinaryExpression1","1"));

        //Expression ::= BinaryExpression2
        re = new ArrayList<RuleElement>();
        r8 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("BinaryExpression2","1"));

        //Expression ::= ParenthesizedExpression
        re = new ArrayList<RuleElement>();
        r9 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("ParenthesizedExpression","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.addRule(r4);
        gf.addRule(r5);
        gf.addRule(r6);
        gf.addRule(r7);
        gf.addRule(r8);
        gf.addRule(r9);
        gf.setStartType("Expression");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        cf.addCompositionPrecedences(r1, r2);
        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

/*
        Iterator<ParsedSymbol> itep;
        ParsedSymbol ps;
        Iterator<ParsedSymbol> itep2;
        ParsedSymbol ps2;
        for (itep = pg.getSymbols().iterator();itep.hasNext();) {
            ps = itep.next();
            System.out.println(""+ps.getType()+"   "+ps.getStartIndex()+"-"+ps.getEndIndex());

            if (pg.getPreceding().get(ps) != null)
            for (itep2 = pg.getPreceding().get(ps).iterator();itep2.hasNext();) {
                ps2 = itep2.next();
                System.out.println("    a preceding: "+ps2.getType()+"   "+ps2.getStartIndex()+"-"+ps2.getEndIndex());
            }

            if (pg.getFollowing().get(ps) != null)
            for (itep2 = pg.getFollowing().get(ps).iterator();itep2.hasNext();) {
                ps2 = itep2.next();
                System.out.println("    a following: "+ps2.getType()+"   "+ps2.getStartIndex()+"-"+ps2.getEndIndex());
            }

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());

        Symbol s;
        Iterator<Symbol> ite;
        for (ite = sg.getSymbols().iterator();ite.hasNext();) {
            s = ite.next();
            System.out.println(""+s+"    "+s.getType()+"   "+s.getStartIndex()+"-"+s.getEndIndex());

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
*/
/*


        Iterator<Symbol> ite;
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
*/
/*
  Iterator<Symbol> ite;
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
        */
        assertEquals(1,sg.getRoots().size());
        assertEquals(12,sg.getSymbols().size());

        assertNotNull(searchSymbol(sg,0,0,"Integer"));
        assertNotNull(searchSymbol(sg,1,1,"Operator1"));
        assertNotNull(searchSymbol(sg,2,2,"Integer"));
        assertNotNull(searchSymbol(sg,3,3,"Operator2"));
        assertNotNull(searchSymbol(sg,4,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Integer"));
        assertNull(searchSymbol(sg,3,4,"Integer"));

        assertNull(searchSymbol(sg,1,2,"Expression"));

        assertNull(searchSymbol(sg,3,4,"Expression"));

        assertNotNull(searchSymbol(sg,0,0,"Expression"));
        assertNotNull(searchSymbol(sg,2,2,"Expression"));
        assertNotNull(searchSymbol(sg,4,4,"Expression"));

        assertNotNull(searchSymbol(sg,0,2,"Expression"));
        assertNotNull(searchSymbol(sg,0,2,"BinaryExpression1"));

        assertNotNull(searchSymbol(sg,0,4,"Expression"));
        assertNotNull(searchSymbol(sg,0,4,"BinaryExpression2"));

        assertNull(searchSymbol(sg,1,4,"Expression"));

        assertEquals(searchSymbol(sg,3,3,"Operator2"),searchSymbolFirstContent(sg,0,4,"BinaryExpression2",searchSymbol(sg,0,2,"Expression")).getContents().get(1));
        assertEquals(searchSymbol(sg,4,4,"Expression"),searchSymbolFirstContent(sg,0,4,"BinaryExpression2",searchSymbol(sg,0,2,"Expression")).getContents().get(2));
    }

    @Test public void SelectionPrecedenceTest1() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15,r16,r17;
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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

        //Statement ::= FunctionCallStatement
        re = new ArrayList<RuleElement>();
        r16 = new Rule(new RuleElement("Statement"),re);
        re.add(new RuleElementPosition("FunctionCallStatement","1"));

        //OutputStatement ::= output LeftParenthesis Expression RightParenthesis Semicolon
        re = new ArrayList<RuleElement>();
        r17 = new Rule(new RuleElement("FunctionCallStatement"),re);
        re.add(new RuleElementPosition("Identifier","1"));
        re.add(new RuleElementPosition("LeftParenthesis","2"));
        re.add(new RuleElementPosition("Expression","3"));
        re.add(new RuleElementPosition("RightParenthesis","4"));
        re.add(new RuleElementPosition("Semicolon","5"));

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
        gf.addRule(r16);
        gf.addRule(r17);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        cf.addSelectionPrecedences(r4, r17);

        Constraints c;


        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(38,sg.getSymbols().size());
        assertEquals(1,sg.getRoots().size());
        assertNotNull(searchSymbol(sg,0,3,"main"));
        assertNotNull(searchSymbol(sg,5,5,"LeftBracket"));
        assertNotNull(searchSymbol(sg,7,11,"input"));
        assertNotNull(searchSymbol(sg,12,12,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,13,13,"Identifier"));
        assertNotNull(searchSymbol(sg,14,14,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,15,15,"Semicolon"));
        assertNotNull(searchSymbol(sg,17,21,"input"));
        assertNotNull(searchSymbol(sg,22,22,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,23,23,"Identifier"));
        assertNotNull(searchSymbol(sg,24,24,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,25,25,"Semicolon"));
        assertNotNull(searchSymbol(sg,27,31,"input"));
        assertNotNull(searchSymbol(sg,32,32,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,33,34,"Identifier"));
        assertNotNull(searchSymbol(sg,35,35,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,36,36,"Semicolon"));
        assertNotNull(searchSymbol(sg,38,43,"output"));
        assertNotNull(searchSymbol(sg,44,44,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,45,45,"Integer"));
        assertNotNull(searchSymbol(sg,46,46,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,47,47,"Semicolon"));
        assertNotNull(searchSymbol(sg,49,49,"RightBracket"));

        assertNotNull(searchSymbol(sg,45,45,"Expression"));

        assertNotNull(searchSymbol(sg,7,15,"InputStatement"));
        assertEquals(5,searchSymbol(sg,7,15,"InputStatement").getElements().size());
        assertEquals("input",searchSymbol(sg,7,15,"InputStatement").getElements().get(0).getType());
        assertEquals("LeftParenthesis",searchSymbol(sg,7,15,"InputStatement").getElements().get(1).getType());
        assertEquals("Identifier",searchSymbol(sg,7,15,"InputStatement").getElements().get(2).getType());
        assertEquals("RightParenthesis",searchSymbol(sg,7,15,"InputStatement").getElements().get(3).getType());
        assertEquals("Semicolon",searchSymbol(sg,7,15,"InputStatement").getElements().get(4).getType());
        assertNotNull(searchSymbol(sg,17,25,"InputStatement"));
        assertNotNull(searchSymbol(sg,27,36,"InputStatement"));
        assertNotNull(searchSymbol(sg,38,47,"OutputStatement"));

        assertNotNull(searchSymbol(sg,7,15,"Statement"));
        assertNotNull(searchSymbol(sg,17,25,"Statement"));
        assertNotNull(searchSymbol(sg,27,36,"Statement"));
        assertNotNull(searchSymbol(sg,38,47,"Statement"));

        assertNull(searchSymbol(sg,7,15,"StatementListAny"));
        assertNull(searchSymbol(sg,17,25,"StatementListAny"));
        assertNull(searchSymbol(sg,27,36,"StatementListAny"));
        assertNotNull(searchSymbol(sg,38,47,"StatementListAny"));

        assertNull(searchSymbol(sg,7,25,"StatementListAny"));
        assertNull(searchSymbol(sg,17,36,"StatementListAny"));
        assertNotNull(searchSymbol(sg,27,47,"StatementListAny"));

        assertNull(searchSymbol(sg,7,36,"StatementListAny"));
        assertNotNull(searchSymbol(sg,17,47,"StatementListAny"));

        assertNotNull(searchSymbol(sg,7,47,"StatementListAny"));

        assertNotNull(searchSymbol(sg,7,47,"StatementList"));

        assertNotNull(searchSymbol(sg,0,49,"Start"));
        assertEquals(4,searchSymbol(sg,0,49,"Start").getElements().size());
        assertEquals("main",searchSymbol(sg,0,49,"Start").getElements().get(0).getType());
        assertEquals("LeftBracket",searchSymbol(sg,0,49,"Start").getElements().get(1).getType());
        assertEquals("StatementList",searchSymbol(sg,0,49,"Start").getElements().get(2).getType());
        assertEquals("RightBracket",searchSymbol(sg,0,49,"Start").getElements().get(3).getType());
    }


    @Test public void SelectionPrecedenceTest2() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15,r16,r17;
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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

        //Statement ::= FunctionCallStatement
        re = new ArrayList<RuleElement>();
        r16 = new Rule(new RuleElement("Statement"),re);
        re.add(new RuleElementPosition("FunctionCallStatement","1"));

        //OutputStatement ::= output LeftParenthesis Expression RightParenthesis Semicolon
        re = new ArrayList<RuleElement>();
        r17 = new Rule(new RuleElement("FunctionCallStatement"),re);
        re.add(new RuleElementPosition("Identifier","1"));
        re.add(new RuleElementPosition("LeftParenthesis","2"));
        re.add(new RuleElementPosition("Expression","3"));
        re.add(new RuleElementPosition("RightParenthesis","4"));
        re.add(new RuleElementPosition("Semicolon","5"));

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
        gf.addRule(r16);
        gf.addRule(r17);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        cf.addSelectionPrecedences(r2, r16);

        Constraints c;


        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(38,sg.getSymbols().size());
        assertEquals(1,sg.getRoots().size());
        assertNotNull(searchSymbol(sg,0,3,"main"));
        assertNotNull(searchSymbol(sg,5,5,"LeftBracket"));
        assertNotNull(searchSymbol(sg,7,11,"input"));
        assertNotNull(searchSymbol(sg,12,12,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,13,13,"Identifier"));
        assertNotNull(searchSymbol(sg,14,14,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,15,15,"Semicolon"));
        assertNotNull(searchSymbol(sg,17,21,"input"));
        assertNotNull(searchSymbol(sg,22,22,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,23,23,"Identifier"));
        assertNotNull(searchSymbol(sg,24,24,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,25,25,"Semicolon"));
        assertNotNull(searchSymbol(sg,27,31,"input"));
        assertNotNull(searchSymbol(sg,32,32,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,33,34,"Identifier"));
        assertNotNull(searchSymbol(sg,35,35,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,36,36,"Semicolon"));
        assertNotNull(searchSymbol(sg,38,43,"output"));
        assertNotNull(searchSymbol(sg,44,44,"LeftParenthesis"));
        assertNotNull(searchSymbol(sg,45,45,"Integer"));
        assertNotNull(searchSymbol(sg,46,46,"RightParenthesis"));
        assertNotNull(searchSymbol(sg,47,47,"Semicolon"));
        assertNotNull(searchSymbol(sg,49,49,"RightBracket"));

        assertNotNull(searchSymbol(sg,45,45,"Expression"));

        assertNotNull(searchSymbol(sg,7,15,"InputStatement"));
        assertEquals(5,searchSymbol(sg,7,15,"InputStatement").getElements().size());
        assertEquals("input",searchSymbol(sg,7,15,"InputStatement").getElements().get(0).getType());
        assertEquals("LeftParenthesis",searchSymbol(sg,7,15,"InputStatement").getElements().get(1).getType());
        assertEquals("Identifier",searchSymbol(sg,7,15,"InputStatement").getElements().get(2).getType());
        assertEquals("RightParenthesis",searchSymbol(sg,7,15,"InputStatement").getElements().get(3).getType());
        assertEquals("Semicolon",searchSymbol(sg,7,15,"InputStatement").getElements().get(4).getType());
        assertNotNull(searchSymbol(sg,17,25,"InputStatement"));
        assertNotNull(searchSymbol(sg,27,36,"InputStatement"));
        assertNotNull(searchSymbol(sg,38,47,"OutputStatement"));

        assertNotNull(searchSymbol(sg,7,15,"Statement"));
        assertNotNull(searchSymbol(sg,17,25,"Statement"));
        assertNotNull(searchSymbol(sg,27,36,"Statement"));
        assertNotNull(searchSymbol(sg,38,47,"Statement"));

        assertNull(searchSymbol(sg,7,15,"StatementListAny"));
        assertNull(searchSymbol(sg,17,25,"StatementListAny"));
        assertNull(searchSymbol(sg,27,36,"StatementListAny"));
        assertNotNull(searchSymbol(sg,38,47,"StatementListAny"));

        assertNull(searchSymbol(sg,7,25,"StatementListAny"));
        assertNull(searchSymbol(sg,17,36,"StatementListAny"));
        assertNotNull(searchSymbol(sg,27,47,"StatementListAny"));

        assertNull(searchSymbol(sg,7,36,"StatementListAny"));
        assertNotNull(searchSymbol(sg,17,47,"StatementListAny"));

        assertNotNull(searchSymbol(sg,7,47,"StatementListAny"));

        assertNotNull(searchSymbol(sg,7,47,"StatementList"));

        assertNotNull(searchSymbol(sg,0,49,"Start"));
        assertEquals(4,searchSymbol(sg,0,49,"Start").getElements().size());
        assertEquals("main",searchSymbol(sg,0,49,"Start").getElements().get(0).getType());
        assertEquals("LeftBracket",searchSymbol(sg,0,49,"Start").getElements().get(1).getType());
        assertEquals("StatementList",searchSymbol(sg,0,49,"Start").getElements().get(2).getType());
        assertEquals("RightBracket",searchSymbol(sg,0,49,"Start").getElements().get(3).getType());
    }

    @Test public void ChainedSelectionPrecedenceTest() {
        TokenSpecification m1,m2,m3;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        Rule r1,r2,r3;
        String input = "abc";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("A",new RegExpPatternRecognizer("abc"),TokenOption.CONSIDER,null);
        m2 = new TokenSpecification("B",new RegExpPatternRecognizer("abc"),TokenOption.CONSIDER,null);
        m3 = new TokenSpecification("C",new RegExpPatternRecognizer("abc"),TokenOption.CONSIDER,null);

        lsf.addTokenSpecification(m1);
        lsf.addTokenSpecification(m2);
        lsf.addTokenSpecification(m3);

        LexicalSpecification ls;
        try {
            ls = lsf.create();
        } catch (TokenSpecificationCyclicPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("A","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("B","1"));

        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("C","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        ConstraintsFactory cf = new ConstraintsFactory();

        cf.addSelectionPrecedences(r1, r2);
        cf.addSelectionPrecedences(r2, r3);

        Constraints c;


        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(4,sg.getSymbols().size());
        assertEquals(2,sg.getRoots().size());
        assertNotNull(searchSymbol(sg,0,2,"A"));
        assertNull(searchSymbol(sg,0,3,"B"));
        assertNotNull(searchSymbol(sg,0,2,"C"));
        assertNotNull(searchSymbol(sg,0,2,"Start"));
        assertEquals(2,countSymbols(sg,0,2,"Start"));

    }


    @Test public void EmptySymbolTest() {
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
        //gf.addRule(r3);
        //gf.addRule(r4);
        //gf.addRule(r5);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(1,sg.getRoots().size());
    }


    @Test public void EmptySymbolTest2() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
        re.add(new RuleElementPosition("Separator","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Separator"),re);

        /*
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
        r5 = new Rule(new RuleElement("Separator3"),re);*/


        gf.addRule(r1);
        gf.addRule(r2);
        //gf.addRule(r3);
        //gf.addRule(r4);
        //gf.addRule(r5);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(1,sg.getRoots().size());

    }
    
    
    @Test public void OptionalTest1() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "main 3";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
        r2 = new Rule(new RuleElement("Empty"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(0,sg.getRoots().size());

    }
    
    @Test public void OptionalTest2() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "3 main";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
        r2 = new Rule(new RuleElement("Empty"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(0,sg.getRoots().size());

    }    
    
    

    
    @Test public void OptionalTest3() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "main 3";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Empty","1"));
        re.add(new RuleElementPosition("main","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Empty"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(0,sg.getRoots().size());

    }
    
    @Test public void OptionalTest4() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "3 main";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("Empty","1"));
        re.add(new RuleElementPosition("main","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Empty"),re);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(0,sg.getRoots().size());

    }        
    

    @Test public void BuilderTest1() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("main2",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
        r2 = new Rule(new RuleElement("Start"),re,null,new SymbolBuilder() {

            /**
             * Serial Version ID
             */
            private static final long serialVersionUID = 31415926535897932L;

            @Override
            public boolean build(Symbol t,Object data) {
                return false;
            }
        });
        re.add(new RuleElementPosition("main2","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(1,sg.getRoots().size());

    }          
    
    
    @Test public void BuilderTest2() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("main2",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
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
        r2 = new Rule(new RuleElement("Start"),re,null,new SymbolBuilder() {

            /**
             * Serial Version ID
             */
            private static final long serialVersionUID = 31415926535897932L;

            @Override
            public boolean build(Symbol t,Object data) {
                return true;
            }
        });
        re.add(new RuleElementPosition("main2","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(2,sg.getRoots().size());

    }      
    


    @Test public void PostBuilderTest1() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("main2",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        CountSymbolBuilder csb = new CountSymbolBuilder();
        
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Interm"),re,null,null,new PostSymbolBuilder() {

            /**
             * Serial Version ID
             */
            private static final long serialVersionUID = 31415926535897932L;

            @Override
            public boolean build(Symbol t,Object data,Map<Symbol,Set<Symbol>> usedIn) {
                return false;
            }
        });
        re.add(new RuleElementPosition("main","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re,null,csb);
        re.add(new RuleElementPosition("Interm","1"));

        
        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg = fce.enforce(c, pg);

        assertEquals(0,sg.getRoots().size());
        assertEquals(1,csb.getCount());

    }          
  
    @Test public void BuilderCountTest() {
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m7,m16,m17,m18;
        Rule r1,r2;
        String input = "main";
        StringReader sr = new StringReader(input);

        m1 = new TokenSpecification("Space",new RegExpPatternRecognizer(" +"),TokenOption.IGNORE,null);
        m2 = new TokenSpecification("Tab",new RegExpPatternRecognizer("\\t+"),TokenOption.IGNORE,null);
        m3 = new TokenSpecification("NewLine",new RegExpPatternRecognizer("\\n|\\r+"),TokenOption.IGNORE,null);
        m4 = new TokenSpecification("Integer",new RegExpPatternRecognizer("(-|\\+)?[0-9]+"),TokenOption.CONSIDER,null);
        m5 = new TokenSpecification("Real",new RegExpPatternRecognizer("(-|\\+)?[0-9]+\\.[0-9]*"),TokenOption.CONSIDER,null);
        m7 = new TokenSpecification("main2",new RegExpPatternRecognizer("main"),TokenOption.CONSIDER,null);
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        CountSymbolBuilder csb = new CountSymbolBuilder();

        //Statement ::= InputStatement
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("Start"),re);
        re.add(new RuleElementPosition("main","1"));

        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("Start"),re,null,csb);
        re.add(new RuleElementPosition("main2","1"));

        gf.addRule(r1);
        gf.addRule(r2);
        gf.setStartType("Start");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        fce.enforce(c, pg);

        assertEquals(1,csb.getCount());

    }      
    
    @Test public void BuilderBoundTest() {

        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8,m9,m10,m11,m12,m13,m14,m15,m16,m17,m18,m19;
        Rule r1,r2,r3,r4,r5,r6,r7,r8,r9;
        String input = "5+3+2+1+2+1+1+1+5+3+2+1+2+1+1+1+5+3+2+1+2+1+1+1+5+3+2+1+2+1+1+1+5+3+2+1+2+1+1+1";
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
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }



        //We define the syntactic rules:
        GrammarFactory gf = new GrammarFactory();
        List<RuleElement> re;

        CountSymbolBuilder csb = new CountSymbolBuilder();

        //Expression ::= Expression Operator1 Expression
        re = new ArrayList<RuleElement>();
        r1 = new Rule(new RuleElement("BinaryExpression1"),re,null,csb);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator1","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= Expression Operator2 Expression
        re = new ArrayList<RuleElement>();
        r2 = new Rule(new RuleElement("BinaryExpression2"),re,null,csb);
        re.add(new RuleElementPosition("Expression","1"));
        re.add(new RuleElementPosition("Operator2","2"));
        re.add(new RuleElementPosition("Expression","3"));

        //Expression ::= LeftParenthesis Expression RightParenthesis
        re = new ArrayList<RuleElement>();
        r3 = new Rule(new RuleElement("ParenthesizedExpression"),re,null,csb);
        re.add(new RuleElementPosition("LeftParenthesis","1"));
        re.add(new RuleElementPosition("Expression","2"));
        re.add(new RuleElementPosition("RightParenthesis","3"));

        //Expression ::= Operator1 Expression
        re = new ArrayList<RuleElement>();
        r4 = new Rule(new RuleElement("UnaryExpression"),re);
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

        //Expression ::= BinaryExpression1
        re = new ArrayList<RuleElement>();
        r7 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("BinaryExpression1","1"));
        r7.setRelevant(0);

        //Expression ::= BinaryExpression2
        re = new ArrayList<RuleElement>();
        r8 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("BinaryExpression2","1"));
        r8.setRelevant(0);

        //Expression ::= ParenthesizedExpression
        re = new ArrayList<RuleElement>();
        r9 = new Rule(new RuleElement("Expression"),re);
        re.add(new RuleElementPosition("ParenthesizedExpression","1"));
        r9.setRelevant(0);

        gf.addRule(r1);
        gf.addRule(r2);
        gf.addRule(r3);
        gf.addRule(r4);
        gf.addRule(r5);
        gf.addRule(r6);
        gf.addRule(r7);
        gf.addRule(r8);
        gf.addRule(r9);
        gf.setStartType("Expression");

        Grammar g;
        try {
            g = gf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = fgp.parse(g, lg);

        ConstraintsFactory cf = new ConstraintsFactory();

        cf.setAssociativity("Operator1", AssociativityConstraint.LEFT_TO_RIGHT);
        cf.setAssociativity("Operator2", AssociativityConstraint.LEFT_TO_RIGHT);
        Constraints c;

        try {
            c = cf.create();
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceConstraintEnforcerTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        fce.enforce(c, pg);

        assertTrue(csb.getCount()<100);
    }      
    
        
}



        /*
        Iterator<Symbol> ite;
        for (ite = sg.getRoots().iterator();ite.hasNext();) {
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            System.out.println(" ");
            recShow(ite.next(),0);

        }
        System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
         */

