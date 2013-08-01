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
public class Ini5 implements IModel {

    public Set<Ino> a;

    @Constraint
    private boolean run() {
        return true;
    }
}
