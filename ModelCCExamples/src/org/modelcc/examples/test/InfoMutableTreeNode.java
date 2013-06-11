package org.modelcc.examples.test;

import javax.swing.tree.DefaultMutableTreeNode;

public class InfoMutableTreeNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class languageClass;
	private String languageName;

	public InfoMutableTreeNode(String text,Class languageClass,String languageName) {
		super(text);
		this.languageClass = languageClass;
		this.languageName = languageName;
	}
	
	public Class getLanguageClass() {
		return languageClass;
	}

	public String getLanguageName() {
		return languageName;
	}
	
}
