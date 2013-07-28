/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.lamb.probabilistic;

import java.io.Reader;

import org.modelcc.lexer.lamb.LambSafe;
import org.modelcc.lexer.lamb.Token;
import org.modelcc.lexer.recognizer.MatchedObject;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.modelcc.language.lexis.LexicalSpecification;
import org.modelcc.language.lexis.TokenOption;
import org.modelcc.language.lexis.TokenSpecification;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 * PLamb - Probabilistic Lexer with AMBiguity Support - Contents.
 * @author elezeta
 * @serial
 */
public final class ProbabilisticLambSafe extends LambSafe implements Serializable {

    /**
     * Builds a token, filling its data, and validates it.
     * @param t token to be built.
     * @return true if the token is valid, false if not
     */
    @Override
    protected boolean build(TokenSpecification m,Token t) {
    	//TODO probabilistic
        return m.getBuilder().build(t);
    }

}
