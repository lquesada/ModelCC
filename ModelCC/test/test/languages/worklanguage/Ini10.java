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
public class Ini10 implements IModel {

	@Multiplicity(minimum=2,maximum=4)
    @Prefix("\\(")
    @Suffix("\\)")
    public Set<Ino2> a;

    Ino2 b;

    @Constraint
    private boolean run() {
        return true;
    }
}
