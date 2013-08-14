/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.lexis;

import java.io.Serializable;
import org.modelcc.lexer.lamb.Token;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 * Token specification.
 * @author elezeta
 * @serial
 */
public final class TokenSpecification implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Pattern recognizer.
     */
    private PatternRecognizer recognizer;

    /**
     * Type of this matchable item.
     * Can be used to store anything that identifies this token.
     */
    private Object type;

    /**
     * Whether if the element has to be considered or not.
     */
    private TokenOption option;

    /**
     * Token builder.
     */
    private TokenBuilder builder;

    /**
     * Default token builder.
     */
    private static TokenBuilder defaultBuilder = new TokenBuilder() {

        /**
         * Serial Version ID
         */
        private static final long serialVersionUID = 31415926535897932L;

        @Override
		public boolean build(Token t) {
            return true;
        }
    };

    /**
     * Constructor.
     * @param rec the pattern recognizer to be used.
     * @param type the element type. Can be used to store anything that
     *             identifies this token.
     * @param builder the token builder.
     */
    public TokenSpecification(Object type,PatternRecognizer rec,TokenBuilder builder) {
        this.type = type;
        recognizer = rec;
        this.builder = builder;
        if (this.builder == null)
            this.builder = defaultBuilder;
        this.option = TokenOption.CONSIDER;
    }

    /**
     * Constructor.
     * @param rec the pattern recognizer to be used.
     * @param type the element type. Can be used to store anything that
     *             identifies this token.
     * @param option if the element has to be ignored (false) or not (true).
     * @param builder the token builder.
     */
    public TokenSpecification(Object type,PatternRecognizer rec,TokenOption option,TokenBuilder builder) {
        this.type = type;
        recognizer = rec;
        this.builder = builder;
        if (this.builder == null)
            this.builder = defaultBuilder;
        this.option = option;
    }

    /**
     * Constructor.
     * @param rec the pattern recognizer to be used.
     * @param type the element type. Can be used to store anything that
     *             identifies this token.
     * @param option if the element has to be ignored (false) or not (true).
     */
    public TokenSpecification(Object type,PatternRecognizer rec,TokenOption option) {
        this(type,rec,option,null);
    }

    /**
     * Constructor.
     * @param rec the pattern recognizer to be used.
     * @param type the element type. Can be used to store anything that
     *             identifies this token.
     */
    public TokenSpecification(Object type,PatternRecognizer rec) {
        this(type,rec,TokenOption.CONSIDER);
    }

    /**
     * @return the pattern recognizer.
     */
    public PatternRecognizer getRecognizer() {
        return recognizer;
    }

    /**
     * @return the type of this matcher.
     */
    public Object getType() {
        return type;
    }

    /**
     * @return whether this element has to be considered (true) or not (false).
     */
    public TokenOption getTokenOption() {
        return option;
    }

    /**
     * @param option whether this element has to be considered (true) or not (false).
     */
    public void setTokenOption(TokenOption option) {
        this.option = option;
    }

    /**
     * @return the builder
     */
    public TokenBuilder getBuilder() {
        return builder;
    }

    /**
     * @param builder the builder to set
     */
    public void setBuilder(TokenBuilder builder) {
        this.builder = builder;
        if (this.builder == null)
            this.builder = defaultBuilder;
    }

}
