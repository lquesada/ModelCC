/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.metamodel.ModelElement;

/**
 * Class Finder
 * @author elezeta
 * @serial
 */
public class PrimitiveClasses implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    
    private PrimitiveClasses() {
    	
    }
    

    /**
     * Returns the Class object of a primitive or non-primitive class name.
     * @param className the class name
     * @return the Class object
     * @throws ClassNotFoundException
     */
    public static Class getClass(String className) throws ClassNotFoundException {
        if (!className.contains(".")) {
            if("int" .equals(className)) return int.class;
            if("long".equals(className)) return long.class;
            if("byte".equals(className)) return byte.class;
            if("short".equals(className)) return short.class;
            if("float".equals(className)) return float.class;
            if("double".equals(className)) return double.class;
            if("boolean".equals(className)) return boolean.class;
            if("char".equals(className)) return char.class;
        }
        return Class.forName(className);
    }
}
