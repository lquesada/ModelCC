/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.worklanguage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Prefix("a")
@Suffix({"b","cc"})
public class Ini11 implements IModel {

    @Minimum(2)
    @Maximum(4)
    @Prefix("\\(")
    @Suffix("\\)")
    public Set<Ino2> a;

    Ino b;

    @Setup
    private boolean run() {
        return true;
    }
}
