package org.modelcc.examples.test;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.ImageIcon;
import net.miginfocom.swing.MigLayout;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import java.awt.FlowLayout;

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
		setTitle("ModelCC Examples");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
		mainPanel.setLayout(new BorderLayout(0, 0));
		setContentPane(mainPanel);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		mainPanel.add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel examplesLabel = new JLabel("Examples");
		leftPanel.add(examplesLabel, BorderLayout.NORTH);
		
		JPanel examplesTreePanel = new JPanel();
		examplesTreePanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		leftPanel.add(examplesTreePanel, BorderLayout.CENTER);
		examplesTreePanel.setLayout(new BorderLayout(0, 0));
		
		JTree examplesTree = new JTree();
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
		
		JPanel inputTextPanel = new JPanel();
		inputTextPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		inputPanel.add(inputTextPanel, BorderLayout.CENTER);
		inputTextPanel.setLayout(new BorderLayout(0, 0));
		
		JTextArea inputTextArea = new JTextArea();
		inputTextPanel.add(inputTextArea);
		inputTextArea.setRows(10);
		
		JLabel inputLabel = new JLabel("Input");
		inputPanel.add(inputLabel, BorderLayout.NORTH);
		
		JPanel outputPanel = new JPanel();
		outputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		splitPane.setRightComponent(outputPanel);
		outputPanel.setLayout(new BorderLayout(0, 0));
		
		JLabel outputLabel = new JLabel("Output");
		outputPanel.add(outputLabel, BorderLayout.NORTH);
		
		JPanel outputTextPanel = new JPanel();
		outputTextPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
		outputPanel.add(outputTextPanel, BorderLayout.CENTER);
		outputTextPanel.setLayout(new BorderLayout(0, 0));
		
		JTextArea outputTextArea = new JTextArea();
		outputTextPanel.add(outputTextArea);
		outputTextArea.setEditable(false);
		outputTextArea.setRows(5);
	}

}
