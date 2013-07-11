/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.modelcc.language.syntax.SymbolBuilder;
import org.modelcc.parser.fence.Symbol;

/**
 * Symbol list one builder
 * @author elezeta
 * @serial
 */
public final class ListElementSymbolBuilder extends SymbolBuilder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Builds a symbol, filling its data, and validates it.
     * @param t symbol to be built.
     * @param data the parser data.
     * @return true if the symbol is valid, false if not
     */
    public boolean build(Symbol t,Object data) {
    	System.out.println("TODO OJO Y LOS DELIMITADORES QUE?");
    	if (t.getContents().size()==2) {
	        Object[] l = new Object[1];
	        l[0] = t.getContents().get(t.getContents().size()-1).getUserData();
	        t.setUserData(new ListContents(l,t.getContents().get(0).getUserData()));
	        return true;
    	}
    	else if (t.getContents().size()==3) {
	        ListContents l0 = (ListContents)t.getContents().get(0).getUserData();
	        ListContents l1 = (ListContents)t.getContents().get(t.getContents().size()-1).getUserData();
    		System.out.println("LISTELEMENTSYMBOL HAS 3");
    		System.out.println(t.getContents().get(0).getUserData());
    		System.out.println("has "+l0.getL());
    		System.out.println(t.getContents().get(1).getUserData());
    		System.out.println(t.getContents().get(2).getUserData());
    		System.out.println("has "+l1.getL());
	        Object[] l = new Object[l0.getL().length+l1.getL().length];
	        Object[] rest = l0.getL();
	        for (int i = 0;i < rest.length;i++)
	            l[i] = rest[i];
	        rest = l1.getL();
	        for (int i = 0;i < rest.length;i++)
	            l[i+l0.getL().length] = rest[i];
    		System.out.println("TODO EXTRA");

	        t.setUserData(new ListContents(l,t.getContents().get(1).getUserData())); //TODO EXTRA
	        return true;
    	}
    	else {
	        Object[] l = new Object[1];
	        l[0] = t.getContents().get(0).getUserData();
	        t.setUserData(new ListContents(l));
	        return true;
    	}
    }
}
