/*
 * ModelCC, under ModelCC Shared Software License, www.modelcc.org. Luis Quesada Torres.
 */


package org.modelcc.examples.test;

import javax.swing.tree.DefaultMutableTreeNode;


/**
 * Mutable tree node with file information.
 * @author elezeta
 */
public class InfoMutableTreeNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Class languageClass;
	private String languageName;
	private String langInfo;
	private int textNumber;

	public InfoMutableTreeNode(String text,Class languageClass,String languageName,String langInfo,int textNumber) {
		super(text);
		this.languageClass = languageClass;
		this.languageName = languageName;
		this.langInfo = langInfo;
		this.textNumber = textNumber;
	}
	
	public Class getLanguageClass() {
		return languageClass;
	}

	public String getLanguageName() {
		return languageName;
	}
	
	public String getLanguageInfo() {
		return langInfo;
	}
	
	public int getTextNumber() {
		return textNumber;
	}
	
}
