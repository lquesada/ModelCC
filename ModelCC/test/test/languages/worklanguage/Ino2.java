/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package test.languages.worklanguage;

import org.modelcc.*;

/**
 *
 * @author elezeta
 */
@Pattern(regExp="hello")
@Separator(",")
@Prefix("-")
@Suffix("\\+")
public class Ino2 implements IModel {

    int a = 0;

}
