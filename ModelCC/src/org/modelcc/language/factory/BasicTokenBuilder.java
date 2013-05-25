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
                    fld.set(o, castObject(fld.getType(), t.getValue()));
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

        //System.out.println("Me han llamado!");

        return valid;
    }

    private Object castObject(Class type,Object obj) {
        if (obj.getClass().equals(String.class)) {
            String data = (String)obj;
            if (type.equals(double.class) || type.equals(Double.class)) {
                if (data.charAt(0) == '+')
                    return Double.parseDouble(data.subSequence(1,data.length()).toString());
                else
                    return Double.parseDouble(data.toString());
            }
            else if(type.equals(float.class) || type.equals(Float.class)) {
                if (data.charAt(0) == '+')
                    return Float.parseFloat(data.subSequence(1,data.length()).toString());
                else
                    return Float.parseFloat(data.toString());
            }
            else if (type.equals(long.class) || type.equals(Long.class)) {
                if (data.charAt(0) == '+')
                    return Long.parseLong(data.subSequence(1,data.length()).toString());
                else
                    return Long.parseLong(data.toString());
            }
            else if (type.equals(int.class) || type.equals(Integer.class)) {
                if (data.charAt(0) == '+')
                    return Integer.parseInt(data.subSequence(1,data.length()).toString());
                else
                    return Integer.parseInt(data.toString());
            }
            else if (type.equals(byte.class) || type.equals(Byte.class)) {
                if (data.charAt(0) == '+')
                    return Byte.parseByte(data.subSequence(1,data.length()).toString());
                else
                    return Byte.parseByte(data.toString());
            }            
            else if (type.equals(short.class) || type.equals(Short.class)) {
                if (data.charAt(0) == '+')
                    return Short.parseShort(data.subSequence(1,data.length()).toString());
                else
                    return Short.parseShort(data.toString());
            }
            else if (type.equals(boolean.class) || type.equals(Boolean.class)) return (data.equals("true")?true:false);
            else if (type.equals(String.class)) return data;
            else if (type.equals(Character.class) || type.equals(char.class)) return data.charAt(0);
        }
        return obj;
    }

}
