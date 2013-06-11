package org.modelcc.examples.test;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.ImageIcon;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeWillExpandListener;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.ExpandVetoException;
import javax.swing.tree.TreeSelectionModel;

import org.modelcc.io.ModelReader;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.lexer.recognizer.PatternRecognizer;
import org.modelcc.lexer.recognizer.regexp.RegExpPatternRecognizer;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

public class ModelCCExamplesWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Class languageClass;
    private Parser parser;

	
	private JPanel mainPanel;

	/**
	 * Create the frame.
	 */
	public ModelCCExamplesWindow() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(ModelCCExamplesWindow.class.getResource("/org/modelcc/examples/test/icon.png")));
		setTitle("ModelCC Examples");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		mainPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(mainPanel);
		
		JPanel leftPanel = new JPanel();
		mainPanel.add(leftPanel, BorderLayout.WEST);
		leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel examplesLabel = new JLabel("Examples");
		leftPanel.add(examplesLabel, BorderLayout.NORTH);
		
		JPanel examplesTreePanel = new JPanel();
		examplesTreePanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		leftPanel.add(examplesTreePanel, BorderLayout.CENTER);
		examplesTreePanel.setLayout(new BorderLayout(0, 0));
		
		JTree examplesTree = new JTree();
		examplesTree.setShowsRootHandles(true);
		examplesTree.setRootVisible(false);
		examplesTree.setModel(new DefaultTreeModel(
			new InfoMutableTreeNode("Languages",null,null) {
				{
					InfoMutableTreeNode node_1;
					Class lang;
					String langName;
					
					lang = org.modelcc.examples.language.simplearithmeticexpression.Expression.class;
					langName = "Simple Arithmetic Expression";
					node_1 = new InfoMutableTreeNode(langName,lang,langName);
						node_1.add(new InfoMutableTreeNode("Addition",lang,langName));
						node_1.add(new InfoMutableTreeNode("Subtraction",lang,langName));
						node_1.add(new InfoMutableTreeNode("Nesting",lang,langName));
						node_1.add(new InfoMutableTreeNode("Assoacitivity",lang,langName));
						node_1.add(new InfoMutableTreeNode("Decimal",lang,langName));
					add(node_1);
					
					lang = org.modelcc.examples.language.canvasdraw.CanvasDraw.class;
					langName = "CanvasDraw";
					node_1 = new InfoMutableTreeNode(langName,lang,langName);
						node_1.add(new InfoMutableTreeNode("Blackboard",lang,langName));
						node_1.add(new InfoMutableTreeNode("Polygons",lang,langName));
					add(node_1);
					
					lang = org.modelcc.examples.language.imperativearithmetic.ImperativeArithmetic.class;
					langName = "ImperativeArithmetic";
					node_1 = new InfoMutableTreeNode(langName,lang,langName);
						node_1.add(new InfoMutableTreeNode("Assignment",lang,langName));
					add(node_1);
					
					lang = org.modelcc.examples.language.graphdraw3d.Scene.class;
					langName = "GraphDraw3D";
					node_1 = new InfoMutableTreeNode(langName,lang,langName);
						node_1.add(new InfoMutableTreeNode("Snail",lang,langName));
						node_1.add(new InfoMutableTreeNode("Helix",lang,langName));
						node_1.add(new InfoMutableTreeNode("PalmTree",lang,langName));
					add(node_1);
				}
			}
		));
	    examplesTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		examplesTree.setBorder(new EmptyBorder(5, 5, 5, 0));
		examplesTreePanel.add(examplesTree);
		examplesTree.addTreeWillExpandListener(new TreeWillExpandListener() {
		    public void treeWillExpand(TreeExpansionEvent e) { }
		    public void treeWillCollapse(TreeExpansionEvent e)
		         throws ExpandVetoException {
		     throw new ExpandVetoException(e);
		     }
		    });
		for (int i = 0; i < examplesTree.getRowCount(); i++) {
			examplesTree.expandRow(i);
		}
		final JTree et = examplesTree;
		examplesTree.addTreeSelectionListener(
				new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        InfoMutableTreeNode node = (InfoMutableTreeNode)et.getLastSelectedPathComponent();

		        if (node == null) return;

		        InfoMutableTreeNode nodeInfo = (InfoMutableTreeNode)node;
		        switchLanguage(nodeInfo.getLanguageName(),nodeInfo.getLanguageClass());
		        
		    }

		});
		
		JPanel logoPanel = new JPanel();
		logoPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		leftPanel.add(logoPanel, BorderLayout.SOUTH);
		logoPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel logoLabel = new JLabel("");
		logoPanel.add(logoLabel);
		logoLabel.setIcon(new ImageIcon(ModelCCExamplesWindow.class.getResource("/org/modelcc/examples/test/logo.png")));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setContinuousLayout(true);
		splitPane.setResizeWeight(0.6);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainPanel.add(splitPane, BorderLayout.CENTER);
		
		JPanel inputPanel = new JPanel();
		inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane.setLeftComponent(inputPanel);
		inputPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel inputButtonPanel = new JPanel();
		inputButtonPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		inputPanel.add(inputButtonPanel, BorderLayout.SOUTH);
		inputButtonPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel altEnterPanel = new JPanel();
		altEnterPanel.setBorder(new EmptyBorder(0, 0, 0, 15));
		inputButtonPanel.add(altEnterPanel, BorderLayout.CENTER);
		altEnterPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel altEnterLabel = new JLabel("Press ALT+ENTER in order to");
		altEnterPanel.add(altEnterLabel, BorderLayout.EAST);
		
		JLabel inputLabel = new JLabel("Input");
		inputPanel.add(inputLabel, BorderLayout.NORTH);
		
		JPanel inputBorderPanel = new JPanel();
		inputBorderPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		inputPanel.add(inputBorderPanel, BorderLayout.CENTER);
		inputBorderPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane inputScrollPane = new JScrollPane();
		inputBorderPanel.add(inputScrollPane);
		inputScrollPane.setViewportBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(255, 255, 255)));
		
		inputTextArea = new JTextArea();
		inputScrollPane.setViewportView(inputTextArea);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane.setRightComponent(outputPanel);
		outputPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel outputLabel = new JLabel("Output");
		outputPanel.add(outputLabel, BorderLayout.NORTH);
		
		JPanel outputBorderPanel = new JPanel();
		outputBorderPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		outputPanel.add(outputBorderPanel, BorderLayout.CENTER);
		outputBorderPanel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane outputScrollPane = new JScrollPane();
		outputBorderPanel.add(outputScrollPane);
		outputScrollPane.setViewportBorder(new MatteBorder(5, 5, 5, 5, (Color) new Color(255, 255, 255)));
		

		JButton processButton = new JButton("Process");
		inputButtonPanel.add(processButton, BorderLayout.EAST);
		processButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				process();
			}
		});
		
		outputTextArea = new JTextArea();
		outputTextArea.setEditable(false);
		outputScrollPane.setViewportView(outputTextArea);
		
		outputTextArea.append("Welcome to ModelCC Examples.\n");
		outputTextArea.append("Please choose a language or an example from the left menu.\n");
		outputTextArea.append("\n");
	}

	JTextArea inputTextArea;
	JTextArea outputTextArea;
	
	private void switchLanguage(String languageName,Class languageClass) {
		if (!languageClass.equals(this.languageClass)) {
			outputTextArea.append("Generating parser for the "+languageName+" language.\n\n");
			this.languageClass = languageClass;
	        parser = null;
	        try {
	            ModelReader jmr = new JavaModelReader(languageClass);
	            Model m = jmr.read();
	            Set<PatternRecognizer> ignore = new HashSet<PatternRecognizer>();
	            ignore.add(new RegExpPatternRecognizer("[\r \n\t]+"));
	            ignore.add(new RegExpPatternRecognizer("%[^\n]*(\n|$)"));
	            parser = ParserFactory.create(m,ignore);
	        } catch (Exception ex) {
	        	outputTextArea.append("CRITICAL ERROR: "+ex.getMessage()+"\n");
	        } 
	        outputTextArea.append("Parser generated.\n");
	        outputTextArea.append("\n");
	    }
	}
	

	protected void process() {
        String inp = inputTextArea.getText();
        if (languageClass==null) {
        	outputTextArea.append("No language selected.\n");
        	return;
        }
		
		
        if (languageClass.equals(org.modelcc.examples.language.simplearithmeticexpression.Expression.class)) {
        	org.modelcc.examples.language.simplearithmeticexpression.Expression exp = (org.modelcc.examples.language.simplearithmeticexpression.Expression) parser.parse(inp);
            if (exp == null)
            	outputTextArea.append("null\n");
            else
            	outputTextArea.append(""+exp.eval()+"\n");
        }
        if (languageClass.equals(org.modelcc.examples.language.canvasdraw.CanvasDraw.class)) {
        	org.modelcc.examples.language.canvasdraw.CanvasDraw cd = (org.modelcc.examples.language.canvasdraw.CanvasDraw) parser.parse(inp);
            
            if (cd == null)
            	outputTextArea.append("null\n");
            else {
                JFrame jw = new JFrame("CanvasDraw");
                jw.setResizable(false);
                jw.setSize(cd.getSize());
                Container pane = jw.getContentPane();
                pane.add(cd, BorderLayout.CENTER);
                cd.setVisible(true);
                jw.setVisible(true);
            }
        }
        if (languageClass.equals(org.modelcc.examples.language.imperativearithmetic.ImperativeArithmetic.class)) {
        	org.modelcc.examples.language.imperativearithmetic.ImperativeArithmetic imp = (org.modelcc.examples.language.imperativearithmetic.ImperativeArithmetic) parser.parse(inp);
            if (imp == null)
            	outputTextArea.append("null\n");
            else
            	outputTextArea.append(imp.run()+"\n");
        }
        if (languageClass.equals(org.modelcc.examples.language.graphdraw3d.Scene.class)) {
            //System.out.println("Results: "+parser.parseAll(inp).size());
        	org.modelcc.examples.language.graphdraw3d.Scene imp = (org.modelcc.examples.language.graphdraw3d.Scene) parser.parse(inp);
            if (imp == null)
            	outputTextArea.append("null\n");
            else {
                try {
                	org.modelcc.examples.language.graphdraw3d.resources.DisplayWrapper dw = new org.modelcc.examples.language.graphdraw3d.resources.DisplayWrapper(imp);
                    dw.run();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
}

//TODO when generating parser, clean output
//TODO show description
//TODO show examples
//TODO show ambiguities
//TODO show "opening window"