/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.parser;

import java.io.Serializable;

/**
 * ModelCC Probabilistic Parser
 * @param <T> the parser type
 * @author elezeta
 * @serial
 */
public abstract class ProbabilisticParser<T> extends Parser implements Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

}
