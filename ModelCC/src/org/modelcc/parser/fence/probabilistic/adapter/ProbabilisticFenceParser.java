/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser.fence.probabilistic.adapter;

import java.io.Reader;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.modelcc.language.probabilistic.ProbabilitySpecification;
import org.modelcc.language.syntax.SyntacticSpecification;
import org.modelcc.parser.ParserException;
import org.modelcc.parser.ProbabilisticParser;
import org.modelcc.lexer.Lexer;
import org.modelcc.parser.fence.Symbol;
import org.modelcc.parser.fence.SyntaxGraph;
import org.modelcc.parser.fence.probabilistic.ProbabilisticFence;
import org.modelcc.probabilistic.NumericProbabilityValue;
import org.modelcc.probabilistic.ProbabilityEvaluator;
import org.modelcc.probabilistic.ProbabilityValue;
import org.modelcc.tools.FieldFinder;

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
    protected Lexer gl;

    /**
     * The syntactic specification.
     */
    protected SyntacticSpecification ls;

    /**
     * The probability specification.
     */
    protected ProbabilitySpecification ps;

    /**
     * Constructor
     * @param gl the generic lexer
     * @param gp the generic parser
     * @param ls the language specification
     * @param ps the probability specification
     */
    public ProbabilisticFenceParser(Lexer gl,ProbabilisticFence gp,SyntacticSpecification ls,ProbabilitySpecification ps) {
        this.gl = gl;
        this.gp = gp;
        this.ls = ls;
        this.ps = ps;
    }

    /**
     * Parses an input string
     * @param input the input string
     * @return a collection of parsed objects
     */
    @Override
     public Collection<T> parseAll(Reader input) throws ParserException {
        SyntaxGraph sg = gp.parse(ls,gl.scan(input),objectMetadata);

        List<T> out = new ArrayList<T>();
        
        for (Iterator<Symbol> ite = sg.getRoots().iterator();ite.hasNext();) {
        	Symbol rootSymbol = ite.next();
        	calculateProbability(rootSymbol);
            out.add((T)rootSymbol.getUserData());
        }
        if (out.isEmpty()) {
        	throw new ParserException();
        }

        Comparator c = new Comparator() {

        	@Override
        	public int compare(Object o1, Object o2) {
        		if (((ProbabilityValue)getParsingMetadata(o1).get("probability")).getNumericValue()
        		   >((ProbabilityValue)getParsingMetadata(o2).get("probability")).getNumericValue())
        			return -1;
        		else if (((ProbabilityValue)getParsingMetadata(o1).get("probability")).getNumericValue()
        				   <((ProbabilityValue)getParsingMetadata(o2).get("probability")).getNumericValue())
        			return 1;
        		else
        			return 0;
        	}

        };
        
        Collections.sort(out,c);
        
        return out;
    }

    private void calculateProbability(Symbol symbol) {
        if (symbol.getUserData() == null)
        	return;
        Map<String,Object> symbolMap = objectMetadata.get(symbol.getUserData());
        if (symbolMap == null)
                return;
        if (symbolMap.get("probability") != null)
        	return;
        for (int i = 0;i < symbol.getContents().size();i++)
        	calculateProbability(symbol.getContents().get(i));
		if (symbol.isToken()) {
        	ProbabilityEvaluator pe = ps.getElementProbabilities().get(symbol.getUserData().getClass());
        	if (pe != null)
                symbolMap.put("probability",pe.evaluate(symbol.getUserData()));
        	else
                symbolMap.put("probability",new NumericProbabilityValue(1d));
        }
        else {
        	ProbabilityValue pv = null;
        	ProbabilityEvaluator pe = ps.getElementProbabilities().get(symbol.getUserData().getClass());
        	if (pe != null)
        		pv = pe.evaluate(symbol.getUserData());
        	Field[] fields = FieldFinder.getAllFields(symbol.getUserData().getClass());
        	for (int i = 0;i < fields.length;i++) {
        		Field field = fields[i];
        		Object content;
				try {
					field.setAccessible(true);
					content = field.get(symbol.getUserData());
	        		if (content != null) {
						ProbabilityEvaluator pem = ps.getMemberProbabilities().get(field);
	            		if (pem != null)
	            			pv = addProbability(pv,pem.evaluate(content));

	            		if (Collection.class.isAssignableFrom(content.getClass())) {
							Collection c = (Collection)content;
							for (Iterator ite = c.iterator();ite.hasNext();) {
								Object contentelement = ite.next();
			        			if (objectMetadata.containsKey(contentelement))
			        				pv = addProbability(pv,(ProbabilityValue)objectMetadata.get(contentelement).get("probability"));
								
							}
						}
						else if (content.getClass().isArray()) {
							Object[] array = (Object[])content;
							for (int x = 0;x < array.length;x++) {
			        			if (objectMetadata.containsKey(array[x]))
			        				pv = addProbability(pv,(ProbabilityValue)objectMetadata.get(array[x]).get("probability"));
							}
						}
						else {
		        			if (objectMetadata.containsKey(content))
		        				pv = addProbability(pv,(ProbabilityValue)objectMetadata.get(content).get("probability"));
						}
	        		}
	        		else {
						ProbabilityEvaluator pem = ps.getMemberProbabilities().get(field);
	        			if (pem != null)
	            			pv = addProbability(pv,pem.evaluate(content).complementary());
	        		}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
        	}
            symbolMap.put("probability",pv);
        }
    }

    public ProbabilityValue addProbability(ProbabilityValue one,ProbabilityValue two) {
    	if (one == null)
    		return two;
    	else {
    		if (two == null)
    			return one;
    		else
    			return one.product(two);
    	}
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
