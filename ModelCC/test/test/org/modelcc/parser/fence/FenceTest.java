/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.parser.fence;

import org.modelcc.lexer.Lexer;
import org.modelcc.lexer.lamb.adapter.LambLexer;
import org.modelcc.language.syntax.RuleElementPosition;
import java.io.StringReader;
import org.modelcc.parser.fence.Fence;
import org.modelcc.language.syntax.NullRuleElementException;
import org.modelcc.language.syntax.SyntacticSpecificationFactory;
import org.modelcc.language.syntax.CyclicSelectionPrecedenceException;
import org.modelcc.language.syntax.CyclicCompositionPrecedenceException;
import org.modelcc.language.lexis.TokenSpecificationCyclicPrecedenceException;
import org.modelcc.parser.fence.Symbol;
import org.modelcc.parser.fence.SyntaxGraph;
import org.modelcc.language.syntax.RuleElement;
import org.modelcc.language.syntax.Rule;
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
import org.modelcc.language.syntax.SyntacticSpecification;

import test.org.modelcc.io.Serialization;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public class FenceTest {

    public FenceTest() {
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
    }

    @Test public void ChainedSelectionPrecedenceTest() {
        TokenSpecification m1,m2,m3,m4,m5,m6,m7,m8;
        LexicalSpecificationFactory lsf = new LexicalSpecificationFactory();
        Rule r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15,r16,r17;
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
            Logger.getLogger(FenceTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }

        SyntacticSpecificationFactory ssf = new SyntacticSpecificationFactory();

        //We define the syntactic rules:
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

        ssf.addRule(r1);
        ssf.addRule(r2);
        ssf.addRule(r3);
        ssf.setStartType("Start");

        ssf.addSelectionPrecedence(r1, r2);
        ssf.addSelectionPrecedence(r2, r3);


        SyntacticSpecification ss;
        try {
            ss = ssf.create();
        } catch (NullRuleElementException ex) {
            Logger.getLogger(FenceTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicCompositionPrecedenceException ex) {
            Logger.getLogger(FenceTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        } catch (CyclicSelectionPrecedenceException ex) {
            Logger.getLogger(FenceTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
            return;
        }


        Lexer lamb = new LambLexer(ls,new Lamb());
        LexicalGraph lg = lamb.scan(sr);

        Fence f = new Fence();
        SyntaxGraph sg = f.parse(ss, lg);
        try {
            sg = (SyntaxGraph)Serialization.testSerialize(sg);
        } catch (ClassNotFoundException ex) {
            assertFalse(true);
            Logger.getLogger(FenceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        assertEquals(4,sg.getSymbols().size());
        assertEquals(2,sg.getRoots().size());
        assertNotNull(searchSymbol(sg,0,2,"A"));
        assertNull(searchSymbol(sg,0,3,"B"));
        assertNotNull(searchSymbol(sg,0,2,"C"));
        assertNotNull(searchSymbol(sg,0,2,"Start"));
        assertEquals(2,countSymbols(sg,0,2,"Start"));

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