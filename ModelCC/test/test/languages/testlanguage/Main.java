/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.testlanguage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Main implements IModel {

    @Prefix({"a","b"})
    @Suffix({"c","d"})
    @Separator({"e","a"})
    @Minimum(0)
    @Maximum(1000)
    @Optional
    Test1[] tests1;

    @Minimum(1)
    List<Test1> tests2;

    @Maximum(10)
    @Reference
    List<Test3> tests3;

    Set<Test2> tests4;

    @ID
    @Reference
    Test5 tests5;

    
    Map<Test1,Integer> testsout1;

    Map<Test1,Test1> testsout2;

    Map<Integer,Test1> testsout3;

    Set<Set<Test1>> testsout4;

    @Constraint
    boolean run() {
        return true;
    }

    @Setup
    void setup() {
    	
    }
}
