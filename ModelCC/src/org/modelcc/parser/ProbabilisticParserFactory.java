/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelcc.language.LanguageSpecification;
import org.modelcc.language.factory.CannotGenerateLanguageSpecificationException;
import org.modelcc.language.factory.LanguageSpecificationFactory;
import org.modelcc.lexer.Lexer;
import org.modelcc.lexer.lamb.adapter.LambLexer;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.metamodel.BasicModelElement;
import org.modelcc.metamodel.ChoiceModelElement;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.parser.fence.Fence;
import org.modelcc.parser.fence.adapter.FenceParser;
import org.modelcc.parser.fence.adapter.FenceParserFactory;
import org.modelcc.parser.fence.probabilistic.adapter.ProbabilisticFenceParserFactory;

/**
 * ModelCC Parser Generator
 * @author elezeta
 * @serial
 */
public class ProbabilisticParserFactory implements Serializable {


    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Creates a probabilistic parser (convenience method)
     * @param m the model
     * @return the parser
     * @throws CannotCreateParserException
     */
    public static ProbabilisticParser create(Model m) throws CannotCreateParserException {
        return ProbabilisticFenceParserFactory.create(m);
    }

    /**
     * Creates a probabilistic parser (convenience method)
     * @param m the model
     * @param lexer the lexer
     * @return the parser
     * @throws CannotCreateParserException
     */
    public static ProbabilisticParser create(Model m,Lexer lexer) throws CannotCreateParserException {
    	return ProbabilisticFenceParserFactory.create(m,lexer);
    }

    /**
     * Creates a probabilistic parser (convenience method)
     * @param m the model
     * @param skip the skip model
     * @return the parser
     * @throws CannotCreateParserException
     */
    public static ProbabilisticParser create(Model m,Model skip) throws CannotCreateParserException {
    	return ProbabilisticFenceParserFactory.create(m,skip);
    }

    /**
     * Creates a probabilistic parser (convenience method)
     * @param m the model
     * @param ignore the ignore set
     * @return the parser
     * @throws CannotCreateParserException
     */
    public static ProbabilisticParser create(Model m,Set<PatternRecognizer> ignore) throws CannotCreateParserException {
    	return ProbabilisticFenceParserFactory.create(m,ignore);
    }
	
}