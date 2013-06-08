/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.testlanguage;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Associativity(AssociativityType.RIGHT_TO_LEFT)
@Composition(CompositionType.LAZY)
@Prefix({})
@Suffix({"d","c"})
@Separator({})
@Priority(value=2,precedes={Test6.class})
@FreeOrder(false)
public class Test4 extends Test2 implements IModel {

    @Constraint boolean run2() {
        return true;
    }
}
