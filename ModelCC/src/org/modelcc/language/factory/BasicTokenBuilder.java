/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.modelcc.language.lexis.TokenBuilder;
import org.modelcc.metamodel.BasicModelElement;
import org.modelcc.metamodel.ModelElement;
import org.modelcc.lexer.lamb.Token;
import org.modelcc.metamodel.Model;
import org.modelcc.tools.FieldFinder;

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
    @Override
	public boolean build(Token t) {
        ElementId eid = (ElementId)t.getType();
        BasicModelElement be = (BasicModelElement) eid.getElement();
        Class c = be.getElementClass();
        boolean valid = true;
        Object o;
        try {
            o = c.newInstance();

            if (be.getValueField() != null) {
            	Field fld = FieldFinder.findField(c,be.getValueField());
                if (fld != null) {
                    fld.setAccessible(true);
                    fld.set(o, ObjectCaster.castObject(fld.getType(), t.getValue()));
                }
            }
            runSetupMethods(o,be);
            valid &= runConstraints(o,be);
            t.setUserData(o);

        } catch (Exception ex) {
            Logger.getLogger(BasicTokenBuilder.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return valid;
    }

	private void runSetupMethods(Object o,ModelElement el) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (m.getSuperelements().get(el) != null) {
			runSetupMethods(o,m.getSuperelements().get(el));
		}
        if (el.getSetupMethod() != null) {
            Method mtd = el.getElementClass().getDeclaredMethod(el.getSetupMethod(),new Class[]{});
            if (mtd != null) {
                mtd.setAccessible(true);
                try {
                    mtd.invoke(o);
				} catch (IllegalArgumentException e) {
	                Logger.getLogger(CompositeSymbolBuilder.class.getName()).log(Level.SEVERE, "Exception when invoking method \"{0}\" of class class \"{1}\".", new Object[]{mtd.getName(),el.getElementClass().getCanonicalName()});
					e.printStackTrace();
					throw e;
				} catch (IllegalAccessException e) {
	                Logger.getLogger(CompositeSymbolBuilder.class.getName()).log(Level.SEVERE, "Exception when invoking method \"{0}\" of class class \"{1}\".", new Object[]{mtd.getName(),el.getElementClass().getCanonicalName()});
					e.printStackTrace();
					throw e;
				} catch (InvocationTargetException e) {
	                Logger.getLogger(CompositeSymbolBuilder.class.getName()).log(Level.SEVERE, "Exception when invoking method \"{0}\" of class class \"{1}\".", new Object[]{mtd.getName(),el.getElementClass().getCanonicalName()});
					e.printStackTrace();
					throw e;
				}
            }
        }
	}

	private boolean runConstraints(Object o, ModelElement el) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		boolean valid = true;
		if (m.getSuperelements().get(el) != null) {
			valid &= runConstraints(o,m.getSuperelements().get(el));
		}
        for (int i = 0;i < el.getConstraintMethods().size();i++) {
            Method mtd = el.getElementClass().getDeclaredMethod(el.getConstraintMethods().get(i),new Class[]{});
            if (mtd != null) {
                mtd.setAccessible(true);
                try {
                    valid &= (Boolean)mtd.invoke(o);
				} catch (IllegalArgumentException e) {
	                Logger.getLogger(CompositeSymbolBuilder.class.getName()).log(Level.SEVERE, "Exception when invoking method \"{0}\" of class class \"{1}\".", new Object[]{mtd.getName(),el.getElementClass().getCanonicalName()});
					e.printStackTrace();
					throw e;
				} catch (IllegalAccessException e) {
	                Logger.getLogger(CompositeSymbolBuilder.class.getName()).log(Level.SEVERE, "Exception when invoking method \"{0}\" of class class \"{1}\".", new Object[]{mtd.getName(),el.getElementClass().getCanonicalName()});
					e.printStackTrace();
					throw e;
				} catch (InvocationTargetException e) {
	                Logger.getLogger(CompositeSymbolBuilder.class.getName()).log(Level.SEVERE, "Exception when invoking method \"{0}\" of class class \"{1}\".", new Object[]{mtd.getName(),el.getElementClass().getCanonicalName()});
					e.printStackTrace();
					throw e;
				}
            }
        }
		return valid;
	}


}
