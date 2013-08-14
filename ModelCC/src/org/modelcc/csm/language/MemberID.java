/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.csm.language;

import java.io.Serializable;
import org.modelcc.*;

/**
 * Element Name class.
 * @author elezeta
 * @serial
 */
public class MemberID implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

    @Prefix("\\[")
	@Suffix("\\]")
	private MemberName memberName;

}
