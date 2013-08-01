/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.worklanguage;

import java.util.HashSet;
import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Ini6 implements IModel {

    public HashSet<Ino> a;

    @Constraint
    private boolean run() {
        return true;
    }
}
