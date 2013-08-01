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
public class Ini61 implements IModel {

    public HashSet<Ino1> a;

    @Constraint
    private boolean run() {
        return true;
    }
}
