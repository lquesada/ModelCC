/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.worklanguage;

import java.util.Set;
import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Prefix("a")
@Suffix({"b","cc"})
public class Ini11 implements IModel {

	@Multiplicity(minimum=2,maximum=4)
    @Prefix("\\(")
    @Suffix("\\)")
    public Set<Ino2> a;

    Ino b;

    @Constraint
    private boolean run() {
        return true;
    }
}
