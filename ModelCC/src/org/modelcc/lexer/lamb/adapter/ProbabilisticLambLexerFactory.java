/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.lamb.adapter;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelcc.language.LanguageSpecification;
import org.modelcc.language.factory.LanguageSpecificationFactory;
import org.modelcc.lexer.CannotCreateLexerException;
import org.modelcc.lexer.LexerFactory;
import org.modelcc.lexer.lamb.ProbabilisticLamb;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.metamodel.BasicModelElement;
import org.modelcc.metamodel.ChoiceModelElement;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;

/**
 * Probabilistic Lamb Lexer Generator
 * @author elezeta
 * @serial
 */
public class ProbabilisticLambLexerFactory extends LambLexerFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Creates a lamb lexer
     * @param m the model
     * @return the created lamb lexer
     * @throws CannotCreateLexerException  
     */
    public static ProbabilisticLambLexer create(Model m) throws CannotCreateLexerException {
        return ProbabilisticLambLexerFactory.create(m,new HashSet<PatternRecognizer>());
    }
    
    /**
     * Creates a lamb lexer
     * @param m the model
     * @param skip the skip model.
     * @return the created lamb lexer
     * @throws CannotCreateLexerException  
     */
    public static ProbabilisticLambLexer create(Model m,Model skip) throws CannotCreateLexerException {
        Set<PatternRecognizer> ignore = new HashSet<PatternRecognizer>();
        if (skip != null)
            fillIgnore(ignore,skip,skip.getStart());
        return ProbabilisticLambLexerFactory.create(m,ignore);
    }
      
    /**
     * Creates a lamb lexer
     * @param m the model
     * @param ignore the ignore set.
     * @return the created lamb lexer
     * @throws CannotCreateLexerException  
     */
    public static ProbabilisticLambLexer create(Model m,Set<PatternRecognizer> ignore) throws CannotCreateLexerException {
        try {
            LanguageSpecificationFactory lsf = new LanguageSpecificationFactory();
            LanguageSpecification ls = lsf.create(m);

            ProbabilisticLambLexer lexer = new ProbabilisticLambLexer(ls.getLexicalSpecification(),ignore,new ProbabilisticLamb());
            return lexer;        

        } catch (Exception e) {
            throw new CannotCreateLexerException(e);
        }
    }
    
}
