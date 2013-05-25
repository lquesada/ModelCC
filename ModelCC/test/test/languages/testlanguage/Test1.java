/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.testlanguage;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Prefix({"a1","b1"})
@Suffix({"c1","d1"})
@Separator({"e","a1"})
@Associativity(AssociativityType.NON_ASSOCIATIVE)
public class Test1 implements IModel {

    @Value int a;
}
