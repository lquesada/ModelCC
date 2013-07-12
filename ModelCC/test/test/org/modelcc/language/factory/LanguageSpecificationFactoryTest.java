/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.language.factory;

import test.languages.optionaltest.OptionalTestLanguage2;
import test.languages.keys.Keys11Lang;
import test.languages.keys.Keys10Lang;
import test.languages.keys.Keys9Lang;
import test.languages.keys.Keys8Lang;
import test.languages.keys.Keys7Lang;
import test.languages.keys.Keys6Lang;
import test.languages.keys.Keys5Lang;
import test.languages.keys.Keys4Lang;
import test.languages.keys.Keys3Lang;
import test.languages.keys.Keys2Lang;

import org.modelcc.Minimum;
import org.modelcc.Position;
import org.modelcc.language.factory.CompositeSymbolBuilder;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import test.languages.keys.Keys1Lang;
import test.languages.keys.Keys1;
import test.languages.autorun.AutorunTests;
import test.languages.optionaltest2.OptionalTest2Language;
import test.languages.optionaltest.OptionalTestLanguage;
import test.languages.positions.A;
import test.languages.positions.B;
import test.languages.positions.C;
import test.languages.positions.Position1;
import test.languages.positions.Position10;
import test.languages.positions.Position11;
import test.languages.positions.Position12;
import test.languages.positions.Position13;
import test.languages.positions.Position14;
import test.languages.positions.Position15;
import test.languages.positions.Position16;
import test.languages.positions.Position17;
import test.languages.positions.Position18;
import test.languages.positions.Position19;
import test.languages.positions.Position2;
import test.languages.positions.Position20;
import test.languages.positions.Position3;
import test.languages.positions.Position4;
import test.languages.positions.Position5;
import test.languages.positions.Position6;
import test.languages.positions.Position7;
import test.languages.positions.Position8;
import test.languages.positions.Position8;
import test.languages.positions.Position9;
import test.languages.positions.PositionFree1;

import org.modelcc.lexer.Lexer;
import org.modelcc.lexer.lamb.adapter.LambLexer;
import test.languages.testlanguage.Test7_2;
import test.languages.testlanguage.Test7_1;
import test.languages.testlanguage.Test7;
import java.io.StringReader;
import org.modelcc.language.factory.LanguageSpecificationFactory;
import test.languages.composition3.CondSentence3;
import test.languages.composition2.CondSentence2;
import test.languages.composition3.Composition3;
import test.languages.composition2.Composition2;
import test.languages.composition.Composition1;
import test.languages.arithmeticcalculator2.Expression2;
import test.languages.worklanguage.Ini11;
import test.languages.worklanguage.Ini10;
import test.languages.worklanguage.Ini9;
import test.languages.worklanguage.Ini8;
import test.languages.worklanguage.Ini7;
import test.languages.worklanguage.Ini61;
import test.languages.worklanguage.Ini6;
import test.languages.worklanguage.Ini5;
import test.languages.worklanguage.Ini4;
import test.languages.worklanguage.Ini3;
import java.util.ArrayList;
import test.languages.worklanguage.Ini2;
import test.languages.worklanguage.Ini1;
import test.languages.worklanguage.Ino;
import test.languages.worklanguage.Ini;
import test.languages.arithmeticcalculator.Expression;
import test.org.modelcc.io.Serialization;

import org.modelcc.parser.fence.Symbol;
import org.modelcc.lexer.lamb.LexicalGraph;
import org.modelcc.parser.fence.FenceConstraintEnforcer;
import org.modelcc.parser.fence.ParsedGraph;
import org.modelcc.parser.fence.FenceGrammarParser;
import org.modelcc.parser.fence.Fence;
import org.modelcc.parser.fence.SyntaxGraph;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.language.LanguageSpecification;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import java.util.Set;
import java.util.Iterator;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class LanguageSpecificationFactoryTest {

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

    private class CountFilter implements Filter {

        boolean show;

        private int count;

        public CountFilter(boolean show) {
            this.show = show;
        }

        public boolean isLoggable(LogRecord record) {
            if (record.getLevel() == Level.SEVERE) {
                count++;
            }
            if (show) {
                return true;
            }
            else
                return false;
        }

        int getCount() { return count; }

    }

    public Set<Object> testFull(String input,Class cl) {
        return testFull(input,cl,false);
    }

    public Set<Object> testFull(String input,Class cl,boolean show) {

        JavaModelReader jmr = new JavaModelReader(cl);
        Model m = null;
        try {
            m = jmr.read();
            m = (Model)Serialization.testSerialize(m);
        } catch (Exception e) {
            Logger.getLogger(LanguageSpecificationFactoryTest.class.getName()).log(Level.SEVERE, null, e);
            assertFalse(true);
            return null;
        }

        LanguageSpecificationFactory mlsc = new LanguageSpecificationFactory();
        LanguageSpecification ls = null;
        try {
            ls = mlsc.create(m);
        } catch (Exception e) {
            Logger.getLogger(LanguageSpecificationFactoryTest.class.getName()).log(Level.SEVERE, null, e);
            assertFalse(true);
            return null;
        }

        LexicalGraph lg = null;
        Set<PatternRecognizer> ignore = new HashSet<PatternRecognizer>();
        ignore.add(new RegExpPatternRecognizer("\\t"));
        ignore.add(new RegExpPatternRecognizer(" "));
        ignore.add(new RegExpPatternRecognizer("\n"));
        ignore.add(new RegExpPatternRecognizer("\r"));
        StringReader sr = new StringReader(input);
        Lexer l = new LambLexer(ls.getLexicalSpecification(),ignore);
        try {
            lg = l.scan(sr);
        } catch (Exception e) {
            Logger.getLogger(LanguageSpecificationFactoryTest.class.getName()).log(Level.SEVERE, null, e);
            assertFalse(true);
            return null;
        }

        
        Fence p = new Fence();
        SyntaxGraph sg = null;
        try {
            sg = p.parse(ls.getSyntacticSpecification(),lg);
        } catch (Exception e) {
            Logger.getLogger(LanguageSpecificationFactoryTest.class.getName()).log(Level.SEVERE, null, e);
            assertFalse(true);
            return null;
        }

        // Alternative way
/*        FenceGrammarParser fgp = new FenceGrammarParser();
        ParsedGraph pg = null;
        try {
            pg = fgp.parse(ls.getSyntacticSpecification().getGrammar(),lg);
        } catch (Exception e) {
            Logger.getLogger(LanguageSpecificationFactoryTest.class.getName()).log(Level.SEVERE, null, e);
            assertFalse(true);
            return null;
        }

        FenceConstraintEnforcer fce = new FenceConstraintEnforcer();
        SyntaxGraph sg2 = null;
        try {
            sg2 = fce.enforce(ls.getSyntacticSpecification().getConstraints(),pg);
        } catch (Exception e) {
            Logger.getLogger(LanguageSpecificationFactoryTest.class.getName()).log(Level.SEVERE, null, e);
            assertFalse(true);
            return null;
        }

        assertTrue(sg.getRoots().size() == sg2.getRoots().size());
        assertTrue(sg.getSymbols().size() == sg2.getSymbols().size());
         * 
         */
/*
        System.out.println(ls.getLexicalSpecification().getTokenSpecifications().size()+ " token specifications");
        System.out.println("Found "+lg.getTokens().size()+ " tokens");
        System.out.println("Found "+lg.getRoots().size()+ " first tokens");

        for (Iterator<Token> ite = lg.getTokens().iterator();ite.hasNext();) {
            Token t = ite.next();
            System.out.println(t.getRoots()+"-"+t.getEnd()+"  "+t.getValue()+"  "+showType(t.getType()));

        }

        System.out.println("");
        System.out.println("");
        System.out.println("");
*/
        /*
        System.out.println("Found "+pg.getSymbols().size()+ " pg symbols");
        System.out.println("Found "+pg.getRoots().size()+ " pg root symbols");
        System.out.println(ls.getSyntacticSpecification().getGrammar().getRules().size()+ " rules");
        System.out.println(ls.getSyntacticSpecification().getGrammar().getEmptyRules().size()+ " empty rules");
        System.out.println("Found "+sg.getSymbols().size()+ " symbols");
        System.out.println("Found "+sg.getRoots().size()+ " root symbols");
*/
        if (show) {
            for (Iterator<Symbol> ite = sg.getRoots().iterator();ite.hasNext();) {
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                System.out.println(" ");
                recShow(ite.next(),0);

            }
            //System.out.println(pg.getSymbols().size()+"  "+sg.getSymbols().size());
        }
        
        Set<Object> out = new HashSet<Object>();
        for (Iterator<Symbol> ite = sg.getRoots().iterator();ite.hasNext();) {
            Symbol s = ite.next();
//            System.out.println("RESULTADO: "+s.getUserData());
            out.add(s.getUserData());
           // if (!s.getType().getClass().equals(ElementId.class))
           //     System.out.println(s.getStartIndex()+"-"+s.getEndIndex()+"  "+s.getUserData()+"  "+((ElementId)s.getType()).getElement().getElementClass().getName());
        }
        return out;
    }

    private static void recShow(Symbol t, int tab) {
        int i;
        for (i = 0;i < tab;i++)
            System.out.print("    ");
        if (t == null)
            System.out.println("%null%");
        else {
            System.out.println(t.getType()+"    "+t.getStartIndex()+"-"+t.getEndIndex());
/*            for (i = 0;i < t.getContents().size();i++) {
                recShow(t.getContents().get(i),tab+1);
            }*/
        }

    }

    void checkExpression(String str,double value,boolean show) {
        Set<Object> a = testFull(str,Expression.class,show);
        assertTrue(a.size()>=1);
        //System.out.println(str+"  = "+((Expression)a.iterator().next()).eval());
        assertTrue(((Expression)a.iterator().next()).eval()-0.1<value);
        assertTrue(((Expression)a.iterator().next()).eval()+0.1>value);
    }

    @Test
    public void ModelToLanguageSpecificationTest1() {

        checkExpression("3+5+5",13,false);
        checkExpression("3+5",8,false);
        checkExpression("3",3,false);
        checkExpression("3+(5+5)",13,false);
        checkExpression("3-5+6",4,false);
        checkExpression("3*5+5",20,false);
        checkExpression("3*(5+5)",30,false);
        checkExpression("3/5++(2*5)",10.6,false);
        checkExpression("3*2*5+-2",28,false);
        checkExpression("3+5*5",28,false);
        checkExpression("3+2/6/2",3.16,false);
        checkExpression("3*5*1-5+6*12+5",87,false);
    }

    @Test
    public void ModelToLanguageSpecificationTest1a() {
        assertEquals(0,testFull("3+5+5",Expression2.class).size());
        assertEquals(1,testFull("3+5",Expression2.class).size());
        assertEquals(1,testFull("3",Expression2.class).size());
        assertEquals(1,testFull("3+(5+5)",Expression2.class).size());
        assertEquals(1,testFull("3+5*5",Expression2.class).size());
        assertEquals(1,testFull("3*5+5",Expression2.class).size());
        assertEquals(1,testFull("3*(5+5)",Expression2.class).size());
        assertEquals(0,testFull("3+2/6/2",Expression2.class).size());
        assertEquals(0,testFull("3*5*1-5+6*12+5",Expression2.class).size());
        assertEquals(0,testFull("3*5+1*5+6*12+5",Expression2.class).size());
        assertEquals(1,testFull("(3*5+1*5)+(6*12+5)",Expression2.class).size());
        assertEquals(1,testFull("3/5++2*5",Expression2.class).size());
        assertEquals(1,testFull("(3/5++2*5)",Expression2.class).size());
        assertEquals(1,testFull("(3/5++2*5)+(3/5+2*5)",Expression2.class).size());
        assertEquals(0,testFull("3*2*5+-2",Expression2.class).size());
    }

    @Test
    public void ModelToLanguageSpecificationTest2() {

        Set<Object> o;
        o = testFull("hello",Ini.class);
        assertEquals(1,o.size());
        Ini i = (Ini) o.iterator().next();
        assertEquals(Ino.class,i.a.getClass());

    }

    @Test
    public void ModelToLanguageSpecificationTest3() {

        Set<Object> o;
        o = testFull("hello",Ini1.class);
        assertEquals(1,o.size());
        Ini1 i = (Ini1) o.iterator().next();
        assertEquals(Ino[].class,i.a.getClass());
        assertEquals(1,i.a.length);

    }

    @Test
    public void ModelToLanguageSpecificationTest3a() {

        Set<Object> o;
        o = testFull("hellohello",Ini1.class);
        assertEquals(1,o.size());
        Ini1 i = (Ini1) o.iterator().next();
        assertEquals(Ino[].class,i.a.getClass());
        assertEquals(2,i.a.length);

    }

    @Test
    public void ModelToLanguageSpecificationTest4() {

        Set<Object> o;
        o = testFull("hello",Ini2.class);
        assertEquals(1,o.size());
        Ini2 i = (Ini2) o.iterator().next();
        assertEquals(ArrayList.class,i.a.getClass());
        assertEquals(1,i.a.size());

    }

    @Test
    public void ModelToLanguageSpecificationTest4a() {

        Set<Object> o;
        o = testFull("hellohello",Ini1.class);
        assertEquals(1,o.size());
        Ini1 i = (Ini1) o.iterator().next();
        assertEquals(Ino[].class,i.a.getClass());
        assertEquals(2,i.a.length);

    }

    @Test
    public void ModelToLanguageSpecificationTest5() {

        Set<Object> o;
        o = testFull("hello",Ini3.class);
        assertEquals(1,o.size());
        Ini3 i = (Ini3) o.iterator().next();
        assertEquals(ArrayList.class,i.a.getClass());
        assertEquals(1,i.a.size());

    }

    @Test
    public void ModelToLanguageSpecificationTest5a() {

        Set<Object> o;
        o = testFull("hellohellohello",Ini3.class);
        assertEquals(1,o.size());
        Ini3 i = (Ini3) o.iterator().next();
        assertEquals(ArrayList.class,i.a.getClass());
        assertEquals(3,i.a.size());

    }

    @Test
    public void ModelToLanguageSpecificationTest6() {

        Set<Object> o;
        o = testFull("hellohello",Ini4.class);
        assertEquals(1,o.size());
        Ini4 i = (Ini4) o.iterator().next();
        assertEquals(ArrayList.class,i.a.getClass());
        assertEquals(2,i.a.size());

    }

    @Test
    public void ModelToLanguageSpecificationTest7() {

        Set<Object> o;
        o = testFull("hellohellohellohello",Ini5.class);
        assertEquals(1,o.size());
        Ini5 i = (Ini5) o.iterator().next();
        assertEquals(HashSet.class,i.a.getClass());
        assertEquals(4,i.a.size());

    }

    @Test
    public void ModelToLanguageSpecificationTest8() {

        Set<Object> o;
        o = testFull("hellohellohellohello",Ini6.class);
        assertEquals(1,o.size());
        Ini6 i = (Ini6) o.iterator().next();
        assertEquals(HashSet.class,i.a.getClass());
        assertEquals(4,i.a.size());

    }

    @Test
    public void ModelToLanguageSpecificationTest9() {

        Set<Object> o;
        o = testFull("hellohellohellohello",Ini61.class);
        assertEquals(1,o.size());
        Ini61 i = (Ini61) o.iterator().next();
        assertEquals(HashSet.class,i.a.getClass());
        assertEquals(1,i.a.size());

    }

    @Test
    public void ModelToLanguageSpecificationTest10() {

        Class c = Ini7.class;
        Set<Object> o;
        assertEquals(0,testFull("()",c).size());
        assertEquals(1,testFull("(-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+,-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+,-hello+,-hello+,-hello+)",c).size());

    }


    @Test
    public void ModelToLanguageSpecificationTest11() {

        Class c = Ini8.class;
        Set<Object> o;
        assertEquals(1,testFull("()",c).size());
        assertEquals(1,testFull("(-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+,-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+,-hello+,-hello+,-hello+)",c).size());

    }

    @Test
    public void ModelToLanguageSpecificationTest12() {

        Class c = Ini9.class;
        Set<Object> o;
        assertEquals(0,testFull("()",c).size());
        assertEquals(0,testFull("(-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+)",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+,-hello+)",c).size());
        assertEquals(0,testFull("(-hello+,-hello+,-hello+,-hello+,-hello+)",c).size());
        assertEquals(0,testFull("(-hello+,-hello+,-hello+,-hello+,-hello+,-hello+)",c).size());

    }

    @Test
    public void ModelToLanguageSpecificationTest13() {

        Class c = Ini10.class;
        Set<Object> o;
        assertEquals(0,testFull("()-hello+",c).size());
        assertEquals(0,testFull("(-hello+)-hello+",c).size());
        assertEquals(1,testFull("(-hello+,-hello+)-hello+",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+)-hello+",c).size());
        assertEquals(1,testFull("(-hello+,-hello+,-hello+,-hello+)-hello+",c).size());
        assertEquals(0,testFull("(-hello+,-hello+,-hello+,-hello+,-hello+)-hello+",c).size());
        assertEquals(0,testFull("(-hello+,-hello+,-hello+,-hello+,-hello+,-hello+)-hello+",c).size());

    }

    @Test
    public void ModelToLanguageSpecificationTest14() {

        Class c = Ini11.class;
        Set<Object> o;
        assertEquals(0,testFull("a()hellobcc",c).size());
        assertEquals(0,testFull("a(-hello+)hellobcc",c).size());
        assertEquals(1,testFull("a(-hello+,-hello+)hellobcc",c).size());
        assertEquals(1,testFull("a(-hello+,-hello+,-hello+)hellobcc",c).size());
        assertEquals(1,testFull("a(-hello+,-hello+,-hello+,-hello+)hellobcc",c).size());
        assertEquals(0,testFull("a(-hello+,-hello+,-hello+,-hello+,-hello+)hellobcc",c).size());
        assertEquals(0,testFull("a(-hello+,-hello+,-hello+,-hello+,-hello+,-hello+)hellobcc",c).size());

    }

    @Test
    public void ModelToLanguageSpecificationTest15() {

        Class c = Composition1.class;
        Set<Object> o;
        assertEquals(1,testFull("start s end",c).size());
        assertEquals(1,testFull("start if e s end",c).size());
        assertEquals(1,testFull("start if e s else s end",c).size());
        assertEquals(1,testFull("start if e  if e s else s else s end",c).size());
        assertEquals(2,testFull("start if e  if e s else s end",c).size());

    }

    @Test
    public void ModelToLanguageSpecificationTest16() {

        Class c = Composition2.class;
        Set<Object> o;
        assertEquals(1,testFull("start s end",c).size());
        assertEquals(1,testFull("start if e s end",c).size());
        assertEquals(1,testFull("start if e s else s end",c).size());
        assertEquals(1,testFull("start if e  if e s else s else s end",c).size());
        assertEquals(1,testFull("start if e  if e s else s end",c).size());

        o = testFull("start if e  if e s else s end",c);
        Composition2 cc = (Composition2) o.iterator().next();
        assertNull(((CondSentence2)(cc.sent)).elsesentence);
        assertNotNull(((CondSentence2) ((CondSentence2)(cc.sent)).ifsentence).elsesentence);
    }

    @Test
    public void ModelToLanguageSpecificationTest17() {

        Class c = Composition3.class;
        Set<Object> o;
        assertEquals(1,testFull("start s end",c).size());
        assertEquals(1,testFull("start if e s end",c).size());
        assertEquals(1,testFull("start if e s else s end",c).size());
        assertEquals(1,testFull("start if e  if e s else s else s end",c).size());
        assertEquals(1,testFull("start if e  if e s else s end",c).size());

        o = testFull("start if e  if e s else s end",c);
        Composition3 cc = (Composition3) o.iterator().next();
        assertNotNull(((CondSentence3)(cc.sent)).elsesentence);
        assertNull(((CondSentence3) ((CondSentence3)(cc.sent)).ifsentence).elsesentence);
    }

    @Test
    public void ModelToLanguageSpecificationTest18() {

        Class c = OptionalTestLanguage.class;
        Set<Object> o;
        o = testFull("1",c);
        OptionalTestLanguage cc = (OptionalTestLanguage) o.iterator().next();
        assertNotNull(cc.getTest());
    }
    
    @Test
    public void ModelToLanguageSpecificationTest19() {

        Class c = OptionalTest2Language.class;
        Set<Object> o;
        o = testFull("1",c);
        OptionalTest2Language cc = (OptionalTest2Language) o.iterator().next();
        assertNotNull(cc.getTest());
        assertNotNull(cc.getTest().getTest());
        assertNull(cc.getTest().getTest().getTest());
    }

    @Test
    public void AutorunTest1() {
        assertEquals(1,testFull("a",Test7.class).size());
        assertEquals(0,testFull("b",Test7.class).size());
        assertEquals(1,testFull("a",Test7_1.class).size());
        assertEquals(0,testFull("b",Test7_1.class).size());
        assertEquals(1,testFull("a",Test7_2.class).size());
        assertEquals(1,testFull("b",Test7_2.class).size());
    }

    @Test
    public void AutorunTest2() {

        Class c = AutorunTests.class;
        Set<Object> o;
        o = testFull("a a a",c);
        assertEquals(1,o.size());
        AutorunTests cc = (AutorunTests) o.iterator().next();
        assertEquals(1,cc.a.count);
        assertEquals(1,cc.b.count);
        assertEquals(1,cc.c.count);
        assertEquals(1,cc.a.a.count);
        assertEquals(1,cc.b.a.count);
        assertEquals(1,cc.c.a.count);
    }

    
    @Test
    public void ModelToLanguageSpecificationReferencesWarningTest() {

        Class c = Keys1Lang.class;
        Set<Object> o;
        CountFilter cf = new CountFilter(false);
        Logger.getLogger(CompositeSymbolBuilder.class.getName()).setFilter(cf);
        o = testFull("a1 a2 refs a",c);
        Keys1Lang cc = (Keys1Lang) o.iterator().next();
        assertEquals(cc.keys1[0],cc.refs[0]);
        assertEquals(cf.getCount(),1);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest1() {

        Class c = Keys1Lang.class;
        Set<Object> o;
        o = testFull("a1 refs a",c);
        Keys1Lang cc = (Keys1Lang) o.iterator().next();
        assertEquals(cc.keys1[0],cc.refs[0]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest2() {

        Class c = Keys1Lang.class;
        Set<Object> o;
        o = testFull("a1 refs a a",c);
        Keys1Lang cc = (Keys1Lang) o.iterator().next();
        assertEquals(cc.keys1[0],cc.refs[0]);
        assertEquals(cc.keys1[0],cc.refs[1]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest3() {

        Class c = Keys1Lang.class;
        Set<Object> o;
        o = testFull("a1 b4 refs a a b",c);
        Keys1Lang cc = (Keys1Lang) o.iterator().next();
        assertEquals(cc.keys1[0],cc.refs[0]);
        assertEquals(cc.keys1[0],cc.refs[1]);
        assertEquals(cc.keys1[1],cc.refs[2]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest4() {
        Class c = Keys2Lang.class;
        Set<Object> o;
        o = testFull("a1 refs a a b",c);
        assertFalse(o.iterator().hasNext());
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest5() {
        Class c = Keys3Lang.class;
        Set<Object> o;
        o = testFull("data a1",c);
        assertTrue(o.iterator().hasNext());
    }

    @Test
    public void ModelToLanguageSpecificationReferencesTest6() {
        Class c = Keys3Lang.class;
        Set<Object> o;
        o = testFull("a data a1",c);
        assertTrue(o.iterator().hasNext());
        Keys3Lang cc = (Keys3Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[0],cc.refs[0]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest7() {
        Class c = Keys4Lang.class;
        Set<Object> o;
        o = testFull("a data a1",c);
        assertTrue(o.iterator().hasNext());
        Keys4Lang cc = (Keys4Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[0],cc.refs);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest8() {
        Class c = Keys4Lang.class;
        Set<Object> o;
        o = testFull("b data a1 b2",c);
        assertTrue(o.iterator().hasNext());
        Keys4Lang cc = (Keys4Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[1],cc.refs);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest9() {
        Class c = Keys3Lang.class;
        Set<Object> o;
        o = testFull("b a a data a1 b2",c);
        assertTrue(o.iterator().hasNext());
        Keys3Lang cc = (Keys3Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[1],cc.refs[0]);
        assertEquals(cc.keys1[0],cc.refs[1]);
        assertEquals(cc.keys1[0],cc.refs[2]);
    }
    
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest10() {
        Class c = Keys3Lang.class;
        Set<Object> o;
        o = testFull("a a b data a1 b3",c);
        assertTrue(o.iterator().hasNext());
        Keys3Lang cc = (Keys3Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[0],cc.refs[0]);
        assertEquals(cc.keys1[0],cc.refs[1]);
        assertEquals(cc.keys1[1],cc.refs[2]);

    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest11() {
        Class c = Keys3Lang.class;
        Set<Object> o;
        o = testFull("a a data a1",c);
        assertTrue(o.iterator().hasNext());
        Keys3Lang cc = (Keys3Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[0],cc.refs[0]);
        assertEquals(cc.keys1[0],cc.refs[1]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest12() {
        Class c = Keys5Lang.class;
        Set<Object> o;
        o = testFull("startref b endref data a1 b2",c);
        assertTrue(o.iterator().hasNext());
        Keys5Lang cc = (Keys5Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[1],cc.refs);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest13() {
        Class c = Keys6Lang.class;
        Set<Object> o;
        o = testFull("startref kbc endref data kac1 kbc2",c);
        assertTrue(o.iterator().hasNext());
        Keys6Lang cc = (Keys6Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[1],cc.refs);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest14() {
        Class c = Keys7Lang.class;
        Set<Object> o;
        o = testFull("startref kbc endref data kac1 kbc2",c);
        assertTrue(o.iterator().hasNext());
        Keys7Lang cc = (Keys7Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[1],cc.refs[0]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest15() {
        Class c = Keys7Lang.class;
        Set<Object> o;
        o = testFull("startref kac kbc endref data kbc1 kac2",c);
        assertTrue(o.iterator().hasNext());
        Keys7Lang cc = (Keys7Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[1],cc.refs[0]);
        assertEquals(cc.keys1[0],cc.refs[1]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest16() {
        Class c = Keys7Lang.class;
        Set<Object> o;
        o = testFull("startref kac kbc kdc endref data kbc1 kac2",c);
        assertFalse(o.iterator().hasNext());
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest17() {
        Class c = Keys8Lang.class;
        Set<Object> o;
        o = testFull("kbc1 kac2 refs kac",c);
        assertTrue(o.iterator().hasNext());
        Keys8Lang cc = (Keys8Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.keys1[1],cc.refs[0]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest18() {
        Class c = Keys9Lang.class;
        Set<Object> o;
        o = testFull("[b,a,c] data [a,c,b]:1 [b,a,c]:2",c);
        assertTrue(o.iterator().hasNext());
        Keys9Lang cc = (Keys9Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.data[1],cc.refs[0]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest19() {
        Class c = Keys10Lang.class;
        Set<Object> o;
        o = testFull("[c,b,a,c] data [d,a]:1 [b,c,a,c]:2",c);
        assertTrue(o.iterator().hasNext());
        Keys10Lang cc = (Keys10Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.data[1],cc.refs[0]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest20() {
        Class c = Keys10Lang.class;
        Set<Object> o;
        o = testFull("[b,a,c] data [d,a]:1 [b,c,a,c]:2",c);
        assertTrue(o.iterator().hasNext());
        Keys10Lang cc = (Keys10Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.data[1],cc.refs[0]);
    }
    
    @Test
    public void ModelToLanguageSpecificationReferencesTest21() {
        Class c = Keys11Lang.class;
        Set<Object> o;
        o = testFull("d1 data cval3endval1 val2endval1d",c);
        assertTrue(o.iterator().hasNext());
        Keys11Lang cc = (Keys11Lang) o.iterator().next();
        assertEquals(1,o.size());
        assertEquals(cc.data[1],cc.refs[0]);
    }
    
    @Test
    public void FullOptionalTest() {
        Class c = OptionalTestLanguage2.class;
        Set<Object> o;
        o = testFull("",c);
        assertTrue(o.iterator().hasNext());
        assertNotNull(o.iterator().next());
    }
    
    @Test
    public void PositionTest1() {
        assertEquals(1,testFull("BA",Position1.class).size());
        assertEquals(0,testFull("AB",Position1.class).size());
    }

    @Test
    public void PositionTest2() {
        assertEquals(1,testFull("BA",Position2.class).size());
        assertEquals(0,testFull("AB",Position2.class).size());
    }

    @Test
    public void PositionTest3() {
        assertEquals(1,testFull("BAC",Position3.class).size());
        assertEquals(0,testFull("ABC",Position3.class).size());
    }

    @Test
    public void PositionTest4() {
        assertEquals(1,testFull("BAC",Position4.class).size());
        assertEquals(1,testFull("ABC",Position4.class).size());
    }
    
    @Test
    public void PositionTest5() {
        assertEquals(1,testFull("ACBCCCC",Position5.class).size());
        assertEquals(1,testFull("ACCCCBC",Position5.class).size());
        assertEquals(0,testFull("ACCCCCB",Position5.class).size());
        assertEquals(0,testFull("ABCCCCC",Position5.class).size());
    }

    @Test
    public void PositionTest6() {
        assertEquals(1,testFull("ACCCCBC",Position6.class).size());
        assertEquals(0,testFull("ACBCCCC",Position6.class).size());
        assertEquals(0,testFull("ACCCCCB",Position6.class).size());
        assertEquals(0,testFull("ABCCCCC",Position6.class).size());
    }

    @Test
    public void PositionTest7() {
        assertEquals(1,testFull("ACCCCBC",Position7.class).size());
        assertEquals(1,testFull("ACBCCCC",Position7.class).size());
        assertEquals(1,testFull("ACCCCCB",Position7.class).size());
        assertEquals(1,testFull("ABCCCCC",Position7.class).size());
        assertEquals(0,testFull("BACCCCC",Position7.class).size());
    }

    @Test
    public void PositionTest8() {
        assertEquals(1,testFull("ACCBC",Position8.class).size());
        assertEquals(1,testFull("ACCCCBC",Position8.class).size());
        assertEquals(0,testFull("ACCCCCB",Position8.class).size());
        assertEquals(0,testFull("ABC",Position8.class).size());
        assertEquals(0,testFull("ACBC",Position8.class).size());
    }    

    @Test
    public void PositionTest9() {
        assertEquals(1,testFull("ACxyCxyCxyCxyzBwC",Position9.class).size());
        assertEquals(0,testFull("ACxyzBwC",Position9.class).size());
        assertEquals(0,testFull("ACxyCxyCxyCxyCzBw",Position9.class).size());
        assertEquals(0,testFull("AzBwC",Position9.class).size());
    }    

    @Test
    public void PositionTest10() {
        assertEquals(1,testFull("ACxyCxyCxyCzBwxyC",Position10.class).size());
        assertEquals(0,testFull("ACxyzBwC",Position10.class).size());
        assertEquals(0,testFull("ACxyCxyCxyCxyCzBw",Position10.class).size());
        assertEquals(0,testFull("AzBwC",Position10.class).size());
    }    

    @Test
    public void PositionTest11() {
        assertEquals(1,testFull("ACxyCxyCxyCxyzBwxyC",Position11.class).size());
        assertEquals(0,testFull("ACxyzBwxyC",Position11.class).size());
        assertEquals(0,testFull("ACxyCxyCxyCxyCxyzBw",Position11.class).size());
        assertEquals(0,testFull("AzBwC",Position11.class).size());
    }    

    @Test
    public void PositionTest12() {
        assertEquals(1,testFull("ACxyCxyCxyCzBwC",Position12.class).size());
        assertEquals(0,testFull("ACzBwC",Position12.class).size());
        assertEquals(0,testFull("ACxyCxyCxyCxyCzBw",Position12.class).size());
        assertEquals(0,testFull("AzBwC",Position12.class).size());
    }    

    @Test
    public void PositionTest13() {
        assertEquals(1,testFull("ACxyCxyCxyCxyzBwC",Position13.class).size());
        assertEquals(1,testFull("ACxyCxyzBwCxyCxyC",Position13.class).size());
        assertEquals(1,testFull("ACxyzBwCxyCxyCxyC",Position13.class).size());
    }    

    @Test
    public void PositionTest14() {
        assertEquals(1,testFull("ACxyCxyCxyCzBwxyC",Position14.class).size());
        assertEquals(1,testFull("ACxyCzBwxyCxyCxyC",Position14.class).size());
        assertEquals(1,testFull("ACzBwxyCxyCxyCxyC",Position14.class).size());
    }    

    @Test
    public void PositionTest15() {
        assertEquals(1,testFull("ACxyCxyCxyCzBwC",Position15.class).size());
        assertEquals(1,testFull("ACxyCzBwCxyCxyC",Position15.class).size());
        assertEquals(1,testFull("ACzBwCxyCxyCxyC",Position15.class).size());
    }    

    @Test
    public void PositionTest16() {
        assertEquals(1,testFull("ACxyCxyCxyCxyzBwxyC",Position16.class).size());
        assertEquals(1,testFull("ACxyCxyzBwxyCxyCxyC",Position16.class).size());
        assertEquals(1,testFull("ACxyzBwxyCxyCxyCxyC",Position16.class).size());
    }    

    @Test
    public void PositionTest17() {
        assertEquals(1,testFull("ACxyCxyCxyCxyzBwC",Position17.class).size());
        assertEquals(1,testFull("ACxyCxyzBwCxyCxyC",Position17.class).size());
        assertEquals(1,testFull("ACxyzBwCxyCxyCxyC",Position17.class).size());
        assertEquals(1,testFull("AzBwCxyCxyCxyCxyC",Position17.class).size());
        assertEquals(1,testFull("ACxyCxyCxyCxyCzBw",Position17.class).size());
    }    

    @Test
    public void PositionTest18() {
        assertEquals(1,testFull("ACxyCxyCxyCzBwxyC",Position18.class).size());
        assertEquals(1,testFull("ACxyCzBwxyCxyCxyC",Position18.class).size());
        assertEquals(1,testFull("ACzBwxyCxyCxyCxyC",Position18.class).size());
        assertEquals(1,testFull("AzBwCxyCxyCxyCxyC",Position18.class).size());
        assertEquals(1,testFull("ACxyCxyCxyCxyCzBw",Position18.class).size());
    }    

    @Test
    public void PositionTest19() {
        assertEquals(1,testFull("ACxyCxyCxyCzBwC",Position19.class).size());
        assertEquals(1,testFull("ACxyCzBwCxyCxyC",Position19.class).size());
        assertEquals(1,testFull("ACzBwCxyCxyCxyC",Position19.class).size());
        assertEquals(1,testFull("AzBwCxyCxyCxyCxyC",Position19.class).size());
        assertEquals(1,testFull("ACxyCxyCxyCxyCzBw",Position19.class).size());
    }    

    @Test
    public void PositionTest20() {
        assertEquals(1,testFull("ACxyCxyCxyCxyzBwxyC",Position20.class).size());
        assertEquals(1,testFull("ACxyCxyzBwxyCxyCxyC",Position20.class).size());
        assertEquals(1,testFull("ACxyzBwxyCxyCxyCxyC",Position20.class).size());
        assertEquals(1,testFull("AzBwCxyCxyCxyCxyC",Position20.class).size());
        assertEquals(1,testFull("ACxyCxyCxyCxyCzBw",Position20.class).size());
    }    
    
    @Test
    public void PositionFreeTest1() {
        assertEquals(1,testFull("ACCBC",PositionFree1.class).size());
        assertEquals(1,testFull("ACCCCBC",PositionFree1.class).size());
        assertEquals(1,testFull("CCBCA",PositionFree1.class).size());
        assertEquals(1,testFull("CCCCBCA",PositionFree1.class).size());
    }    
}

/*
        System.out.println(g.getEmptyRules().size()+ " reglas vacías");
        System.out.println(g.getRules().size()+ " reglas rellenas");
        System.out.println(pg.getRoots().size()+ " en el pg");

        for (Iterator<Object> iter = g.getEmptyRules().iterator();iter.hasNext();) {
            System.out.println("vacía: "+iter.next());
        }

        for (Iterator<Rule> iter = g.getRules().iterator();iter.hasNext();) {
            Rule r = iter.next();
            System.out.print("regla: "+r.getLeft().getType()+" ::=");
            for (Iterator<RuleElement> itere = r.getRight().iterator();itere.hasNext();) {
                RuleElement rx = itere.next();
                System.out.print(" "+rx.getType());
            }
            System.out.println();
        }
*/
