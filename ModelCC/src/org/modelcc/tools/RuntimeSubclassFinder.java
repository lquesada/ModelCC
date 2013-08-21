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
public class RuntimeSubclassFinder implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;
    
    private RuntimeSubclassFinder() {
    	
    }
    

    /**
     * Detects all the classes that extend a class or implement an interface in a package.
     * @param pckgname the package name
     * @param tosubclass the class
     * @return a set of extending classes
     * @throws ClassNotFoundException
     */
    public static Set<Class<?>> runTimeFindSubclasses(String pckgname, Class<?> tosubclass) throws ClassNotFoundException {

        Set<Class<?>> ret = new HashSet<Class<?>>();

        String name = pckgname;
		if (!name.startsWith("/"))
		    name = "/" + name;
		name = name.replace('.','/');
	
	  	URL url = JavaModelReader.class.getResource(name);
        if (url == null) {
        	return ret;
        }
	
		File directory = new File(url.getFile());
        if (directory.exists()) { //class is in a directory
		    String [] files = directory.list();
		    for (int i=0;i<files.length;i++) {
				if (files[i].endsWith(".class") && !files[i].contains("<error>")) {
				    String classname = files[i].substring(0,files[i].length()-6);
	                Class<?> newClass = PrimitiveClasses.getClass(pckgname+"."+classname);
	                if (newClass != null) {
	                    if (tosubclass.isAssignableFrom(newClass) && !tosubclass.equals(newClass)) {
	                        ret.add(newClass);
	                    }
	                }
				}
                else {
                    ret.addAll(runTimeFindSubclasses(pckgname+"."+files[i],tosubclass));
                }
		    }
		} else { //class is in a jar file
			JarInputStream jarFile;
            try {
            	jarFile = new JarInputStream(new FileInputStream(tosubclass.getProtectionDomain().getCodeSource().getLocation().toString().substring(5)));
                JarEntry e;
                e = jarFile.getNextJarEntry();
                while (e != null) {
                    try {
                        String entryname = e.getName();
                        if (entryname.endsWith(".class") && !entryname.contains("<error>")) {
                            String classname = entryname.substring(0,entryname.length()-6);
                            if (classname.startsWith("/")) {
                                classname = classname.substring(1);
                            }
                            classname = classname.replace('/','.');
                            Class<?> newClass = PrimitiveClasses.getClass(classname);
                            if (newClass != null) {
                                if (tosubclass.isAssignableFrom(newClass) && !tosubclass.equals(newClass)) {
                                    ret.add(newClass);
                                }
                            }
                        }
                        e = jarFile.getNextJarEntry();
                    } catch (Exception ex) {
                        //ex.printStackTrace();
                    }                    
                }
                jarFile.close();
            } catch (Exception ex) {
                //ex.printStackTrace();
            }                        
		}
        return ret;
    }

}
