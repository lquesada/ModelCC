/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer;

import java.io.Serializable;
import java.util.Set;
import org.modelcc.lexer.lamb.adapter.LambLexerFactory;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.metamodel.Model;

/**
 * ModelCC Parser Generator
 * @author elezeta
 * @serial
 */
public abstract class LexerFactory implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Creates a lexer (convenience method)
     * @param m the model
     * @return the created lexer
     * @throws CannotCreateLexerException  
     */
    public static Lexer create(Model m) throws CannotCreateLexerException {
        return LambLexerFactory.create(m);
    }
    
    /**
     * Creates a lexer (convenience method)
     * @param m the model
     * @param skip the skip model.
     * @return the created lexer
     * @throws CannotCreateLexerException  
     */
    public static Lexer create(Model m,Model skip) throws CannotCreateLexerException {
        return LambLexerFactory.create(m,skip);
    }
      
    /**
     * Creates a lexer (convenience method)
     * @param m the model
     * @param ignore the ignore set.
     * @return the created lexer
     * @throws CannotCreateLexerException  
     */
    public static Lexer create(Model m,Set<PatternRecognizer> ignore) throws CannotCreateLexerException {
        return LambLexerFactory.create(m,ignore);
    }
    
}
