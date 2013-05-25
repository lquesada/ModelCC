/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.syntax;

import java.io.Serializable;

/**
 * Syntactic specification.
 * @author elezeta
 * @serial
 */
public final class SyntacticSpecification implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Grammar.
     */
    private Grammar grammar;

    /**
     * Constraints.
     */
    private Constraints constraints;

    /**
     * Constructor.
     */
    public SyntacticSpecification() {
        this.grammar = null;
        this.constraints = null;
    }

    /**
     * Constructor.
     * @param grammar the grammar.
     * @param constraints the constraints.
     */
    public SyntacticSpecification(Grammar grammar,Constraints constraints) {
        this.grammar = grammar;
        this.constraints = constraints;
    }

    /**
     * @return the grammar
     */
    public Grammar getGrammar() {
        return grammar;
    }

    /**
     * @param grammar the grammar to set
     */
    public void setGrammar(Grammar grammar) {
        this.grammar = grammar;
    }

    /**
     * @return the constraints
     */
    public Constraints getConstraints() {
        return constraints;
    }

    /**
     * @param constraints the constraints to set
     */
    public void setConstraints(Constraints constraints) {
        this.constraints = constraints;
    }
}
