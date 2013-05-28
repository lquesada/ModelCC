/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelcc.language.lexis.TokenBuilder;
import org.modelcc.metamodel.BasicModelElement;
import org.modelcc.lexer.lamb.Token;
import org.modelcc.metamodel.Model;

/**
 * Common token builder
 * @author elezeta
 * @serial
*/
public final class BasicTokenBuilder extends TokenBuilder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * The model.
     */
    Model m;
    
    /**
     * Constructor.
     * @param m the model.
     */
    public BasicTokenBuilder(Model m) {
        this.m = m;
    }
    
    /**
     * Builds a token, filling its data, and validates it.
     * @param t token to be built.
     * @return true if the token is valid, false if not
     */
    public boolean build(Token t) {
        ElementId eid = (ElementId)t.getType();
        BasicModelElement be = (BasicModelElement) eid.getElement();
        Class c = be.getElementClass();
        boolean valid = true;
        Object o;
        try {
            o = c.newInstance();

            if (be.getValueField() != null) {
                Field fld = c.getDeclaredField(be.getValueField());
                if (fld != null) {
                    fld.setAccessible(true);
                    fld.set(o, ObjectCaster.castObject(fld.getType(), t.getValue()));
                }
            }
            if (be.getAutorunMethod() != null) {
                Method mtd = c.getDeclaredMethod(be.getAutorunMethod(),new Class[]{});
                if (mtd != null) {
                    mtd.setAccessible(true);
                    if (mtd.getReturnType().equals(boolean.class) || mtd.getReturnType().equals(Boolean.class)) {
                        valid = (Boolean)mtd.invoke(o);
                    }
                    else {
                        mtd.invoke(o);
                        valid = true;
                    }
                }
            }
            t.setUserData(o);

        } catch (Exception ex) {
            Logger.getLogger(BasicTokenBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return valid;
    }


}
