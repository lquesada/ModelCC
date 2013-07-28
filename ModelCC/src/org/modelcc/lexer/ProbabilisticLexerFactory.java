/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.modelcc.language.LanguageSpecification;
import org.modelcc.language.factory.LanguageSpecificationFactory;
import org.modelcc.lexer.lamb.adapter.LambLexer;
import org.modelcc.lexer.lamb.adapter.LambLexerFactory;
import org.modelcc.lexer.lamb.probabilistic.adapter.ProbabilisticLambLexerFactory;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.metamodel.BasicModelElement;
import org.modelcc.metamodel.ChoiceModelElement;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;

/**
 * ModelCC Parser Generator
 * @author elezeta
 * @serial
 */
public abstract class ProbabilisticLexerFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Creates a probabilistic lexer (convenience method)
     * @param m the model
     * @return the created lexer
     * @throws CannotCreateLexerException  
     */
    public static ProbabilisticLexer create(Model m) throws CannotCreateLexerException {
        return ProbabilisticLambLexerFactory.create(m);
    }
    
    /**
     * Creates a probabilistic lexer (convenience method)
     * @param m the model
     * @param skip the skip model.
     * @return the created lexer
     * @throws CannotCreateLexerException  
     */
    public static ProbabilisticLexer create(Model m,Model skip) throws CannotCreateLexerException {
        return ProbabilisticLambLexerFactory.create(m,skip);
    }
      
    /**
     * Creates a probabilistic lexer (convenience method)
     * @param m the model
     * @param ignore the ignore set.
     * @return the created lexer
     * @throws CannotCreateLexerException  
     */
    public static ProbabilisticLexer create(Model m,Set<PatternRecognizer> ignore) throws CannotCreateLexerException {
        return ProbabilisticLambLexerFactory.create(m,ignore);
    }
    
}
