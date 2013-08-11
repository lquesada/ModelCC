/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.language.factory;

import java.io.Serializable;

/**
 * Object Wrapper
 * @author elezeta
 * @serial
 */
public final class ObjectCaster implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Private constructor as to avoid instantiation.
     */
	private ObjectCaster() { }
	
	/**
	 * Converts and casts an object to a class type
	 * @param type the class type
	 * @param obj the object
	 * @return an instance of the class type.
	 */
    public static Object castObject(Class type,Object obj) {
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
