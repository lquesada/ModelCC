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
public class ClassFinder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    
    private ClassFinder() {
    	
    }
    
    /**
     * Finds the class with a specific name.
     * @param name the class name
     * @throws ClassNotFoundException
     */
    public static Class<?> findClass(String name) throws ClassNotFoundException {
    	return Class.forName(name);
    }

}
