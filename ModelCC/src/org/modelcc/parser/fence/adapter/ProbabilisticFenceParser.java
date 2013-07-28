/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence.adapter;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.modelcc.language.syntax.SyntacticSpecification;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserException;
import org.modelcc.lexer.Lexer;
import org.modelcc.parser.fence.ProbabilisticFence;
import org.modelcc.parser.fence.Symbol;
import org.modelcc.parser.fence.SyntaxGraph;
import org.modelcc.parser.fence.Fence;

/**
 * ModelCC FenceParser
 * @param <T> the results type of the parser
 * @author elezeta
 * @serial
 */
public class ProbabilisticFenceParser<T> extends FenceParser<T> implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Constructor
     * @param gl the generic lexer
     * @param gp the generic parser
     * @param ls the language specification
     */
    public ProbabilisticFenceParser(Lexer gl,ProbabilisticFence gp,SyntacticSpecification ls) {
        this.gl = gl;
        this.gp = gp;
        this.ls = ls;
    }

    /**
     * Fills symbol metadata.
     * @param symbol symbol to analyze.
     * @param symbolMap symbol map in which to store metadata.
     */
    protected void fillMetadata(Symbol symbol, Map<String, Object> symbolMap) {
    	super.fillMetadata(symbol,symbolMap);
    	//TODO store probabilities
	}

}
