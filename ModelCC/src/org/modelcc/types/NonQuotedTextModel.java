package org.modelcc.types;

import java.io.Serializable;

import org.modelcc.*;

@Pattern(regExp="([a-zA-Z0-9_\\-\\!\\@\\$\\#\\`\\~\\%\\^\\&\\*\\(\\)\\=\\+\\[\\]\\{\\}\\\\\\|\\:\\'\\,\\.\\<\\>\\/\\?]([a-zA-Z0-9_\\-\\!\\@\\$\\#\\`\\~\\%\\^\\&\\*\\(\\)\\=\\+\\[\\]\\{\\}\\\\\\|\\:\\'\\\"\\,\\.\\<\\>\\/\\?\\ ]*[a-zA-Z0-9_\\-\\!\\@\\$\\#\\`\\~\\%\\^\\&\\*\\(\\)\\=\\+\\[\\]\\{\\}\\\\\\|\\:\\'\\,\\.\\<\\>\\/\\?])?)?")
public class NonQuotedTextModel extends TextModel implements IModel,Serializable {

    /**
     * Serial Version ID
     */
    private static final long serialVersionUID = 31415926535897932L;

	@Value
	String val;

	@Override
	public String getValue() {
		return val;
	}
	
}
