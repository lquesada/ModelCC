/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language;

import java.io.Serializable;
import org.modelcc.language.lexis.LexicalSpecification;
import org.modelcc.language.probabilistic.ProbabilitySpecification;
import org.modelcc.language.syntax.SyntacticSpecification;

/**
 * Language specification.
 * @author elezeta
 * @serial
 */
public final class LanguageSpecification implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Lexical specification.
     */
    private LexicalSpecification lexicalSpecification;

    /**
     * Syntactic specification.
     */
    private SyntacticSpecification syntacticSpecification;

    /**
     * Probability specification.
     */
    private ProbabilitySpecification probabilitySpecification;

    /**
     * Constructor
     * @param lexicalSpecification the lexical specification.
     * @param syntacticSpecification the syntactic specification.
     * @param probabilitySpecification the probability specification.
     */
    public LanguageSpecification(LexicalSpecification lexicalSpecification,SyntacticSpecification syntacticSpecification,ProbabilitySpecification probabilitySpecification) {
        this.lexicalSpecification = lexicalSpecification;
        this.syntacticSpecification = syntacticSpecification;
        this.probabilitySpecification = probabilitySpecification;
    }

    /**
     * @return the lexicalSpecification
     */
    public LexicalSpecification getLexicalSpecification() {
        return lexicalSpecification;
    }

    /**
     * @return the syntacticSpecification
     */
    public SyntacticSpecification getSyntacticSpecification() {
        return syntacticSpecification;
    }

    /**
     * @return the probabilitySpecification
     */
    public ProbabilitySpecification getProbabilitySpecification() {
        return probabilitySpecification;
    }

}
