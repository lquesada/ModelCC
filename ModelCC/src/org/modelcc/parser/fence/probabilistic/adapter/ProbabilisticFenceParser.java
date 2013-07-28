/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence.probabilistic.adapter;

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
import org.modelcc.parser.ProbabilisticParser;
import org.modelcc.lexer.Lexer;
import org.modelcc.lexer.ProbabilisticLexer;
import org.modelcc.parser.fence.Symbol;
import org.modelcc.parser.fence.SyntaxGraph;
import org.modelcc.parser.fence.Fence;
import org.modelcc.parser.fence.probabilistic.ProbabilisticFence;

/**
 * ModelCC FenceParser
 * @param <T> the results type of the parser
 * @author elezeta
 * @serial
 */
public class ProbabilisticFenceParser<T> extends ProbabilisticParser<T> implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * The generic parser.
     */
    protected ProbabilisticFence gp;

    /**
     * The generic lexer.
     */
    protected ProbabilisticLexer gl;

    /**
     * The syntactic specification.
     */
    protected SyntacticSpecification ls;

    /**
     * Constructor
     * @param gl the generic lexer
     * @param gp the generic parser
     * @param ls the language specification
     */
    public ProbabilisticFenceParser(ProbabilisticLexer gl,ProbabilisticFence gp,SyntacticSpecification ls) {
        this.gl = gl;
        this.gp = gp;
        this.ls = ls;
    }

    /**
     * Parses an input string
     * @param input the input string
     * @return a collection of parsed objects
     */
    @Override
     public Collection<T> parseAll(Reader input) throws ParserException {
        SyntaxGraph sg = gp.parse(ls,gl.scan(input),objectMetadata);
        Set<T> out = new HashSet<T>();
        // TODO change ordering based on probability
        for (Iterator<Symbol> ite = sg.getRoots().iterator();ite.hasNext();) {
        	Symbol rootSymbol = ite.next();
            out.add((T)rootSymbol.getUserData());
        }
        if (out.isEmpty()) {
        	throw new ParserException();
        }
        return out;
    }

	/**
     * Parses an input string
     * @param input the input string
     * @return a parsed object
     */
    @Override
    public T parse(String input) throws ParserException {
        Iterator<T> ite = parseIterator(input);
        if (ite.hasNext())
            return ite.next();
        else
            return null;
    }

    /**
     * Parses an input reader
     * @param input the input reader
     * @return a parsed object
     */
    @Override
    public T parse(Reader input) throws ParserException {
        return parseIterator(input).next();
    }

    /**
     * Parses an input string
     * @param input the input string
     * @return an iterator to the collection of parsed objects
     */
    @Override
    public Iterator<T> parseIterator(String input) throws ParserException {
        return parseAll(input).iterator();
    }

    /**
     * Parses an input reader
     * @param input the input reader
     * @return an iterator to the collection of parsed objects
     */
    @Override
    public Iterator<T> parseIterator(Reader input) throws ParserException {
        return parseAll(input).iterator();
    }

    /**
     * Object metadata warehouse.
     */
    private Map<Object,Map<String,Object>> objectMetadata = new WeakHashMap<Object,Map<String,Object>>();
    
    /**
     * Returns the parsing metadata for an object.
     * @param object an object instantiated during the parsing.
     * @return the parsing metadata.
     */
	@Override
	public Map<String,Object> getParsingMetadata(Object object) {
		if (objectMetadata.get(object) != null)
			return Collections.unmodifiableMap(objectMetadata.get(object));
		return null;
	}

}
