package org.modelcc.examples.test;

import java.awt.BorderLayout;
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
import javax.swing.JEditorPane;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.Toolkit;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;

public class ModelCCExamplesWindow extends JFrame {

	private JPanel mainPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModelCCExamplesWindow frame = new ModelCCExamplesWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		examplesTree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Languages") {
				{
					DefaultMutableTreeNode node_1;
					node_1 = new DefaultMutableTreeNode("Simple Arithmetic Expression");
						node_1.add(new DefaultMutableTreeNode("Addition"));
						node_1.add(new DefaultMutableTreeNode("Subtraction"));
						node_1.add(new DefaultMutableTreeNode("Nesting"));
						node_1.add(new DefaultMutableTreeNode("Assoacitivity"));
						node_1.add(new DefaultMutableTreeNode("Decimal"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("CanvasDraw");
						node_1.add(new DefaultMutableTreeNode("Blackboard"));
						node_1.add(new DefaultMutableTreeNode("Polygons"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("ImperativeArithmetic");
						node_1.add(new DefaultMutableTreeNode("Assignment"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("GraphDraw3D");
						node_1.add(new DefaultMutableTreeNode("Snail"));
						node_1.add(new DefaultMutableTreeNode("Helix"));
						node_1.add(new DefaultMutableTreeNode("PalmTree"));
					add(node_1);
				}
			}
		));
		examplesTree.setBorder(new EmptyBorder(5, 5, 5, 0));
		examplesTreePanel.add(examplesTree);
		
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
		
		JButton Process = new JButton("Process");
		inputButtonPanel.add(Process, BorderLayout.EAST);
		
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
		
		JTextArea inputTextArea = new JTextArea();
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
		
		JTextArea outputTextArea = new JTextArea();
		outputTextArea.setEditable(false);
		outputScrollPane.setViewportView(outputTextArea);
	}
}
