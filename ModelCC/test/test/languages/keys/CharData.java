/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.keys;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="[a-z]")
public class CharData implements IModel {
   
   @Value public char value;
   
}
