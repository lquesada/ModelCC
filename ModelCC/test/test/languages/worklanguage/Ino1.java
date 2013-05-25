/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.worklanguage;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="hello")
public class Ino1 implements IModel {

    int a = 0;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ino1 other = (Ino1) obj;
        if (this.a != other.a) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + this.a;
        return hash;
    }

}
