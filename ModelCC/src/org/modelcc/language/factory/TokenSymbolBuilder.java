/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.Map;
import org.modelcc.language.syntax.SymbolBuilder;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.fence.Symbol;

/**
 * Symbol content builder
 * @author elezeta
 * @serial
 */
public final class TokenSymbolBuilder extends SymbolBuilder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * The model.
     */
    private Model m;
    
    /**
     * Constructor
     * @param m the model.
     */
    public TokenSymbolBuilder(Model m) {
        this.m = m;
    }
    
    /**
     * Builds a token symbol.
     * @param t symbol to be built.
     * @param data the parser listData.
     * @return true if the symbol is valid, false if not
     */
    public boolean build(Symbol t,Object data) {
        Map<Object,ObjectWrapper> map = ((ModelCCParserData)data).getMap();
        map.put(t.getUserData(),new ObjectWrapper(t.getUserData(),m,t.getParsedSymbol().getString().hashCode(),t.getParsedSymbol().getString()));
        return true;
    }
}
