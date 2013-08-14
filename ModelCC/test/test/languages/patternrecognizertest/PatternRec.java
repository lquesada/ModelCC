/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.patternrecognizertest;

import org.modelcc.lexer.recognizer.MatchedObject;
import org.modelcc.lexer.recognizer.PatternRecognizer;

/**
 *
 * @author elezeta
 */
public class PatternRec extends PatternRecognizer {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    public PatternRec(String arg) {
        super(arg);
    }

    @Override
    public MatchedObject read(CharSequence cs, int start) {
        return new MatchedObject(null,cs.toString());
    }

}
