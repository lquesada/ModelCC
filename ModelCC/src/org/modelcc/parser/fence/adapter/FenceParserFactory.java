/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence.adapter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelcc.language.LanguageSpecification;
import org.modelcc.language.factory.CannotGenerateLanguageSpecificationException;
import org.modelcc.metamodel.BasicModelElement;
import org.modelcc.metamodel.ChoiceModelElement;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.parser.fence.Fence;
import org.modelcc.language.factory.LanguageSpecificationFactory;
import org.modelcc.lexer.Lexer;
import org.modelcc.lexer.lamb.adapter.LambLexer;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.parser.CannotCreateParserException;
import org.modelcc.parser.ParserFactory;

/**
 * ModelCC Fence Parser Generator
 * @author elezeta
 * @serial
 */
public final class FenceParserFactory extends ParserFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Creates a parser
     * @param m the model
     * @return the parser
     * @throws CannotCreateParserException
     */
    public static FenceParser create(Model m) throws CannotCreateParserException {
        return create(m,(Set<PatternRecognizer>)null);
    }

    /**
     * Creates a parser
     * @param m the model
     * @param lexer the lexer
     * @return the parser
     * @throws CannotCreateParserException
     */
    public static FenceParser create(Model m,Lexer lexer) throws CannotCreateParserException {
        try {
            //Type erasure does not allow comparing the generated parser with a specific parser type. Check http://serdom.eu/ser/2007/03/25/java-generics-instantiating-objects-of-type-parameter-without-using-class-literal
            LanguageSpecificationFactory lsf = new LanguageSpecificationFactory();
            LanguageSpecification ls = lsf.create(m);
            Fence gp = new Fence();

            FenceParser parser = new FenceParser(lexer,gp,ls.getSyntacticSpecification());
            return parser;
        } catch (CannotGenerateLanguageSpecificationException e) {
            throw new CannotCreateParserException(e);
        }
    }

    /**
     * Creates a parser
     * @param m the model
     * @param skip the skip model
     * @return the parser
     * @throws CannotCreateParserException
     */
    public static FenceParser create(Model m,Model skip) throws CannotCreateParserException {
        Set<PatternRecognizer> ignore = new HashSet<PatternRecognizer>();
        if (skip != null)
            fillIgnore(ignore,skip,skip.getStart());
        return create(m,ignore);
    }

    /**
     * Creates a parser
     * @param m the model
     * @param ignore the ignore set
     * @return the parser
     * @throws CannotCreateParserException
     */
    public static FenceParser create(Model m,Set<PatternRecognizer> ignore) throws CannotCreateParserException {
        try {
            //Type erasure does not allow comparing the generated parser with a specific parser type. Check http://serdom.eu/ser/2007/03/25/java-generics-instantiating-objects-of-type-parameter-without-using-class-literal
            LanguageSpecificationFactory lsf = new LanguageSpecificationFactory();
            LanguageSpecification ls = lsf.create(m);
            LambLexer gl = new LambLexer(ls.getLexicalSpecification(),ignore);
            Fence gp = new Fence();

            FenceParser parser = new FenceParser(gl,gp,ls.getSyntacticSpecification());
            return parser;
        } catch (CannotGenerateLanguageSpecificationException e) {
            throw new CannotCreateParserException(e);
        }
    }

    private static void fillIgnore(Set<PatternRecognizer> ignore, Model skip, ModelElement el) {
        if (el.getClass().equals(ComplexModelElement.class))
            Logger.getLogger(FenceParserFactory.class.getName()).log(Level.SEVERE, "The skip model may not contain composite elements. Element {0} is composite.",new Object[]{el.getElementClass().getCanonicalName()});
        else if (el.getClass().equals(BasicModelElement.class)) {
            ignore.add(((BasicModelElement)el).getPattern());
        }
        else if (el.getClass().equals(ChoiceModelElement.class)) {
            for (Iterator<ModelElement> ite = skip.getSubelements().get(el).iterator();ite.hasNext();)
            fillIgnore(ignore,skip,ite.next());
        }
    }

}
