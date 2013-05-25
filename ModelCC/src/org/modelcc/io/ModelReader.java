/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.io;

import org.modelcc.metamodel.Model;

/**
 * Model writer.
 * @author elezeta
 * @serial
 */
public abstract class ModelReader {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    /**
     * Reads a model.
     * @return the read model
     * @throws Exception
     */
    public abstract Model read() throws Exception;

}
