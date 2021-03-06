/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.modelcc.lexer.Lexer;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExps;
import org.modelcc.metamodel.Model;
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

    public final static Set<PatternRecognizer> WHITESPACE = new HashSet<PatternRecognizer>(Arrays.asList(new RegExpPatternRecognizer(RegExps.tabSpaceNewLines)));

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
