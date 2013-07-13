/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.positions;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="C[0-9]*")
public class C implements IModel {
    @Value
    public String value;
}
