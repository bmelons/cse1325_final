package cse1325final;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Frontend {
	
	private static final String[] HOME_COLUMNS = {"Tests","Shrimp Gumbo"};
	
	public static void main(String[] args) {
		System.out.println("Intializing"); // makin' sure this method even runs before any real code is written, ->
		// -> reloaded the project freshly cloned into a new workspace, $git diff shows
		// no differences so i think we're good
		
		Backend backend = new Backend();

		JFrame frame = makeMainFrame();
		JMenuBar menuBar = makeMenuBar();
		frame.setJMenuBar(menuBar);
		
		// SETUP, probably move to a new block if we don't end up needing to keep these variables around
		// UI elements
		JPanel navbarPanel = new UIPlaceholderButtons(4);
		navbarPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel actionPanel = new UIPlaceholderButtons(3);
		actionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		UIList list = new UIList("Available Tests",HOME_COLUMNS); // TODO: Think about how we want to handle swapping out lists, refresh the existing one or make a new instance?
		list.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		frame.add(navbarPanel,BorderLayout.PAGE_START);
		frame.add(list, BorderLayout.CENTER);
		frame.add(actionPanel,BorderLayout.PAGE_END);
		

		frame.setVisible(true);
		
		// placeholder data
		Object[] row = {"Math for dorks", "Hi"};
		list.addRow(row);
	}
	
	
	private static JFrame makeMainFrame() {
		// internal helper for readability
		// very boilerplate
		
		// using border layout so we can have sometihng like the following
	
		// file | other | menu | options (JMenubar)
		//---------- page selector(PAGE_START, FlowLayout) --------
		//   LIST_(Center)_______________
		//  |list element             	 |
		//  |list element             	 |	
		//  |list element             	 |
		//  |list element             	 |
		//  ----------------------------
		//		action bar (PAGE_END, FlowLayout) account stuff, close, select
		//---------------------------------------------------------
		
		JFrame frame = new JFrame("Testing Software");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(660,780);
		frame.setMinimumSize(new Dimension(550,650));
		
		// learned you have to do a workaround to give the content a margin between it and the border of the app
		JPanel content = new JPanel(new BorderLayout(10,10));
        content.setBorder(new EmptyBorder(10, 20, 10, 20));
        frame.setLayout(new BorderLayout(10, 10));
        frame.setContentPane(content);
        
		
		return frame;
	}
	
	private static JMenuBar makeMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu fileMenu = new JMenu("File");
		
		// TODO: Implement these buttons
		JMenuItem af,ae;
		af = new JMenuItem("A");
		ae = new JMenuItem("B");
		fileMenu.add(af);
		fileMenu.add(ae);
		
		menuBar.add(fileMenu);
		
		return menuBar;
	}

}