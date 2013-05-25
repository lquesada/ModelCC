/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.recognizer.regexp;

import java.io.Serializable;

/**
 * RegExps.
 * @author elezeta
 * @serial
 */
public abstract class RegExps implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Double pattern.
     */
    static public String doublePattern="(-|\\+)?[0-9]+\\.[0-9]*";

    /**
     * Float pattern
     */
    static public String floatPattern="(-|\\+)?[0-9]+\\.[0-9]*";

    /**
     * Long pattern.
     */
    static public String longPattern="(-|\\+)?[0-9]+";

    /**
     * Int pattern.
     */
    static public String intPattern="(-|\\+)?[0-9]+";

    /**
     * Short pattern.
     */
    static public String shortPattern="(-|\\+)?[0-9]+";

    /**
     * Boolean pattern.
     */
    static public String booleanPattern="true|false";

    /**
     * Bar wildcard comment pattern.
     */
    static public String barWildcardCommentPattern="/\\*(?:.|[\\n\\r])*?\\*/";

    /**
     * Double bar pattern.
     */
    static public String doubleBarCommentPattern="(//.*)";

    /**
     * Tab pattern.
     */
    static public String tab="\\t";

    /**
     * Space pattern.
     */
    static public String space=" ";

    /**
     * NewLine pattern.
     */
    static public String newLine="\\n|\\r";

    /**
     * Tab Space Newline pattern.
     */
    static public String tabSpaceNewLines="(\\t| |\\n|\\r)+";

    /**
     * Gets the pattern of a primitive class.
     * @param c the primitive class.
     * @return the pattern of the class.
     */
    static public String getPattern(Class c) {
        if (c.equals(double.class)) return doublePattern;
        if (c.equals(Double.class)) return doublePattern;
        if (c.equals(float.class)) return floatPattern;
        if (c.equals(Float.class)) return floatPattern;
        if (c.equals(long.class)) return longPattern;
        if (c.equals(Long.class)) return longPattern;
        if (c.equals(int.class)) return intPattern;
        if (c.equals(Integer.class)) return intPattern;
        if (c.equals(short.class)) return shortPattern;
        if (c.equals(Short.class)) return shortPattern;
        if (c.equals(boolean.class)) return booleanPattern;
        if (c.equals(Boolean.class)) return booleanPattern;
        return null;
    }

}
