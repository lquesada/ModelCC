/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.worklanguage;

import java.util.List;
import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Ini2 implements IModel {

    public List<Ino> a;

    @Constraint
    private boolean run() {
        return true;
    }
}
