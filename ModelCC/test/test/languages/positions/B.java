/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="B[0-9]*")
public class B implements IModel {
    @Value
    public String value;
}
