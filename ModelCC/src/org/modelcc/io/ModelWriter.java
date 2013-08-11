/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.io;

import java.io.Serializable;

import org.modelcc.metamodel.Model;

/**
 * Model writer.
 * @author elezeta
 * @serial
 */
public abstract class ModelWriter implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Writes a model.
     * @param m the model to write.
     * @throws Exception
     */
    public abstract void write(Model m) throws Exception;

}
