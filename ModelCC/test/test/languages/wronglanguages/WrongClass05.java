/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.wronglanguages;

import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="a",matcher=RegExpPatternRecognizer.class,args="a")
public class WrongClass05 implements IModel {

    OKClass aasdf;
    OKClass aasdf2;

}
