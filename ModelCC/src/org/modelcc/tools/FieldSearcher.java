/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.tools;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author elezeta
 * @serial
 */
public class FieldSearcher implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    private FieldSearcher() { };
    
	public static Field searchField(Class c,String fieldName) {
        try {
			return c.getDeclaredField(fieldName);
		} catch (Exception e) {
			if (c.getSuperclass()!=null)
				return searchField(c.getSuperclass(),fieldName);
            Logger.getLogger(FieldSearcher.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
	}
	
	public static Field[] getAllFields(Class<?> type) {
		List<Field> list = new ArrayList<Field>();
		getAllFields(list,type);
		Field[] fl = new Field[list.size()];
		for (int i = 0;i < list.size();i++)
			fl[i] = list.get(i);
		return fl;
	}

	private static List<Field> getAllFields(List<Field> fields, Class<?> type) {
	    for (Field field: type.getDeclaredFields()) {
	        fields.add(field);
	    }

	    if (type.getSuperclass() != null) {
	        fields = getAllFields(fields, type.getSuperclass());
	    }

	    return fields;
	}

}
