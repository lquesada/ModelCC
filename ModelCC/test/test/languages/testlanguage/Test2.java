/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.testlanguage;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Associativity(AssociativityType.LEFT_TO_RIGHT)
@Composition(CompositionType.EAGER)
@Prefix({"a","b"})
@Suffix({"c","d"})
@Separator("e")
@Priority(value=5,precedes=Test5.class)
@FreeOrder
public class Test2 implements IModel {

    @Autorun
    boolean run() {
        return true;
    }

}
