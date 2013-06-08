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
public class Ini2 implements IModel {

    public List<Ino> a;

    @Setup
    private boolean run() {
        return true;
    }
}
