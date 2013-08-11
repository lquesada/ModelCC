/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc;

import java.io.Serializable;

/**
 * Separator policy.
 * @author elezeta
 */
public enum SeparatorPolicy implements Serializable {
	REPLACE,
	BEFORE,
	AFTER,
	EXTRA
}
