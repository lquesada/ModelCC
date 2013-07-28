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
import org.modelcc.lexer.lamb.Lamb;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.metamodel.BasicModelElement;
import org.modelcc.metamodel.ChoiceModelElement;
import org.modelcc.metamodel.ComplexModelElement;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;

/**
 * Lamb Lexer Generator
 * @author elezeta
 * @serial
 */
public class LambLexerFactory extends LexerFactory implements Serializable {

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
    public static LambLexer create(Model m) throws CannotCreateLexerException {
        return LambLexerFactory.create(m,new HashSet<PatternRecognizer>());
    }
    
    /**
     * Creates a lamb lexer
     * @param m the model
     * @param skip the skip model.
     * @return the created lamb lexer
     * @throws CannotCreateLexerException  
     */
    public static LambLexer create(Model m,Model skip) throws CannotCreateLexerException {
        Set<PatternRecognizer> ignore = new HashSet<PatternRecognizer>();
        if (skip != null)
            fillIgnore(ignore,skip,skip.getStart());
        return LambLexerFactory.create(m,ignore);
    }
      
    /**
     * Creates a lamb lexer
     * @param m the model
     * @param ignore the ignore set.
     * @return the created lamb lexer
     * @throws CannotCreateLexerException  
     */
    public static LambLexer create(Model m,Set<PatternRecognizer> ignore) throws CannotCreateLexerException {
        try {
            LanguageSpecificationFactory lsf = new LanguageSpecificationFactory();
            LanguageSpecification ls = lsf.create(m);
            LambLexer lexer = new LambLexer(ls.getLexicalSpecification(),ignore,new Lamb());
            return lexer;        

        } catch (Exception e) {
            throw new CannotCreateLexerException(e);
        }
    }
    
    /**
     * Fills the ignore set
     * @param ignore the ignore set
     * @param skip the skip model
     * @param el the recursive model element
     */
    protected static void fillIgnore(Set<PatternRecognizer> ignore, Model skip, ModelElement el) {
        if (el.getClass().equals(ComplexModelElement.class))
            Logger.getLogger(LambLexerFactory.class.getName()).log(Level.SEVERE, "The skip model may not contain composite elements. Element {0} is composite.",new Object[]{el.getElementClass().getCanonicalName()});
        else if (el.getClass().equals(BasicModelElement.class)) {
            ignore.add(((BasicModelElement)el).getPattern());
        }
        else if (el.getClass().equals(ChoiceModelElement.class)) {
            for (Iterator<ModelElement> ite = skip.getSubelements().get(el).iterator();ite.hasNext();)
            fillIgnore(ignore,skip,ite.next());
        }
    }

}
