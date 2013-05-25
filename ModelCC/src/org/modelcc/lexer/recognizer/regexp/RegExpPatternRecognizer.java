/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.lexer.recognizer.regexp;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.Serializable;
import org.modelcc.lexer.recognizer.MatchedObject;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 * Regular Expression Based Pattern Recognizer
 * @author elezeta
 * @serial
 */
public final class RegExpPatternRecognizer extends PatternRecognizer implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Static pool of already compiled patterns.
     */
    static HashMap<String,Pattern> patterns = new HashMap<String,Pattern>();

    /**
     * Regular expression.
     */
    private String regExp;

    /**
     * Pattern associated to this recognizer.
     */
    Pattern p;

    /**
     * Regular Expression constructor.
     * @param arg the regular expression that describes this recognizer.
     */
    public RegExpPatternRecognizer(String arg) {
        super(arg);
        this.regExp = arg;
        p = patterns.get(arg);
        if (p == null) {
            p = Pattern.compile(arg);
            patterns.put(arg, p);
        }
    }

    /**
     * Try to match the pattern in a certain position of a char sequence.
     * @param cs the char sequence in which to match the pattern.
     * @param start the position of the char sequence in which to match the pattern.
     * @return an object that contains the matched subsequence if there was a match, null otherwise.
     */
    @Override
    public MatchedObject read(CharSequence cs,int start) {
        return read(new ShiftingCharSequence(cs),start);
    }

    /**
     * Try to match the pattern in a certain position of a char sequence.
     * @param scs the char sequence in which to match the pattern.
     * @param start the position of the char sequence in which to match the pattern.
     * @return an object that contains the matched subsequence if there was a match, null otherwise.
     */
    public MatchedObject read(ShiftingCharSequence scs,int start) {
        boolean result;
        Matcher m;
        scs.shift(start);
        m = p.matcher(scs);
        result = m.lookingAt();
        String s;
        if (result) {
            s = m.group();
            return new MatchedObject(s,s);
        }
        else
            return null;
    }

    /**
     * Equals method
     * @param obj Object to compare with
     * @return true if equals, false if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RegExpPatternRecognizer other = (RegExpPatternRecognizer) obj;
        if (this.p != other.p && (this.p == null || !this.p.equals(other.p))) {
            return false;
        }
        return true;
    }

    /**
     * Unique hashCode
     * @return a hashCode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.p != null ? this.p.hashCode() : 0);
        return hash;
    }

    /**
     * @return the regular expression
     */
    public String getRegExp() {
        return regExp;
    }

    @Override
    /**
     * @return the string.¿Se puede incluir en el contrato una penalización para el caso de que el consumidor 
decida ejercitar el derecho de desistimiento? 
No, además las cláusulas que así lo hagan serán nulas de pleno derecho.     */
    public String toString() {
        return "RE("+regExp+")";
    }
}
