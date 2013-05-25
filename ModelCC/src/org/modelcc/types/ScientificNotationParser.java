/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.types;

import java.io.Serializable;

/**
 * Scientific Notation Parser.
 * @author elezeta
 * @serial
 */
public class ScientificNotationParser implements Serializable {
    
    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private ScientificNotationParser() {
        
    }
        
    public static double parseReal(String text) {
        int pos = -1;
        pos = text.indexOf('e');
        if (pos == -1)
            pos = text.indexOf('E');
        
        String m;
        String e;
        if (pos != -1) {
            m = text.substring(0,pos);
            e = text.substring(pos+1);
        }
        else {
            m = text;
            e = "0";
        }
        if (m.charAt(0)=='+')
            m = m.substring(1);
        if (e.charAt(0)=='+')
            e = e.substring(1);
        double ret = Double.parseDouble(m)*Math.pow(10, Double.parseDouble(e));
        return ret;
    }
}
