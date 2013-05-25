/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.org.modelcc.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import test.org.modelcc.io.java.JavaModelReaderTest;
import static org.junit.Assert.*;

/**
 *
 * @author elezeta
 */
public abstract class Serialization {
    
    public static Object testSerialize(Object o) throws ClassNotFoundException {
        if (o == null)
            return null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream oos;
        try {
            oos = new ObjectOutputStream(out);
            oos.writeObject(o);
            oos.close();
        } catch (Exception e) {
            Logger.getLogger(JavaModelReaderTest.class.getName()).log(Level.SEVERE, null, e);
            assertTrue(false);
        }
        assertTrue(out.toByteArray().length > 0);
        
        byte[] pickled = out.toByteArray();
        InputStream in = new ByteArrayInputStream(pickled);
        ObjectInputStream ois;
        Object o2 = null;
        try {
            ois = new ObjectInputStream(in);
            o2 = ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(JavaModelReaderTest.class.getName()).log(Level.SEVERE, null, ex);
             assertTrue(false);
       }

        return o;
    }
    
}
