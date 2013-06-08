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
public class WrongClass02b implements IModel {

     @Constraint void run1() {
     }

     @Constraint boolean run2() {
         return true;
     }
}
