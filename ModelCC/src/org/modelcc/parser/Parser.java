/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser;

import java.io.Reader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * ModelCC Parser
 * @param <T> the parser type
 * @author elezeta
 * @serial
 */
public abstract class Parser<T> implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Parses an input string
     * @param input the input string
     * @return a parsed object
     */
    public T parse(String input) throws ParserException {
        return parse(new StringReader(input));        
    };

    /**
     * Parses an input reader
     * @param input the input reader
     * @return a parsed object
     */
    public abstract T parse(Reader input) throws ParserException;

    /**
     * Parses an input string
     * @param input the input string
     * @return a collection of parsed objects
     */
    public Collection<T> parseAll(String input) throws ParserException {
        return parseAll(new StringReader(input));
    }

    /**
     * Parses an input reader
     * @param input the input reader
     * @return a collection of parsed objects
     */
    public abstract Collection<T> parseAll(Reader input) throws ParserException;

    /**
     * Parses an input string
     * @param input the input string
     * @return an iterator to the collection of parsed objects
     */
    public Iterator<T> parseIterator(String input) throws ParserException {
        return parseIterator(new StringReader(input));
    }

    /**
     * Parses an input reader
     * @param input the input reader
     * @return an iterator to the collection of parsed objects
     */
    public abstract Iterator<T> parseIterator(Reader input) throws ParserException;

    /**
     * Returns the parsing metadata for an object.
     * @param object an object instantiated during the parsing.
     * @return the parsing metadata.
     */
    public abstract Map<String,Object> getParsingMetadata(Object object);

}
