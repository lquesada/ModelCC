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
public class Ini9 implements IModel {

	@Multiplicity(minimum=2,maximum=4)
    @Prefix("\\(")
    @Suffix("\\)")
    public Set<Ino2> a;

    @Constraint
    private boolean run() {
        return true;
    }
}
