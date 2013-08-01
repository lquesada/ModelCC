/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.worklanguage;

import java.util.ArrayList;
import org.modelcc.*;

/**
 *
 * @author elezeta
 */
public class Ini3 implements IModel {

    public ArrayList<Ino> a;

    @Constraint
    private boolean run() {
        return true;
    }
}
