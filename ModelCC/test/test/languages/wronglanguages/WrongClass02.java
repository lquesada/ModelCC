/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.wronglanguages;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="a")
public class WrongClass02 implements IModel {

     @Setup boolean run1() {
         return true;
     }

     @Setup boolean run2() {
         return true;
     }
}
