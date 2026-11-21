package cse1325final;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Frontend {
	
	private static final String[] HOME_COLUMNS = {"Tests","Shrimp Gumbo"};
	private static JFrame frame;
	public static Backend backend;
	
	public static void main(String[] args) {
		System.out.println("Intializing"); // makin' sure this method even runs before any real code is written, ->
		// -> reloaded the project freshly cloned into a new workspace, $git diff shows
		// no differences so i think we're good
		
		backend = new Backend();

		frame = makeMainFrame();
		JMenuBar menuBar = makeMenuBar();
		frame.setJMenuBar(menuBar);
		
		// SETUP, probably move to a new block if we don't end up needing to keep these variables around
		// UI elements
		// i have realized theres not really a reason to have a navbar..
		//JPanel navbarPanel = new UIFlowPanel();
		
		JPanel actionPanel = new UIFlowPanel();
		makeLowerActionPanelButtons(actionPanel);
		
		
		UIList list = new UIList("Available Tests",HOME_COLUMNS); // TODO: Think about how we want to handle swapping out lists, refresh the existing one or make a new instance?
		
//		frame.add(navbarPanel,BorderLayout.PAGE_START);
		frame.add(list, BorderLayout.CENTER);
		frame.add(actionPanel,BorderLayout.PAGE_END);
		

		frame.setVisible(true);
		
		// placeholder data
		Object[][] rows = {
				{"Math for dorks", "Hi"},
				{"Economics for hobos", "extra spicy"},
				{"Idiot Test", "placehoulder!?"}
				};
		for (Object[] row : rows) {
			list.addRow(row);
		}
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
		af = new JMenuItem("Save");
		ae = new JMenuItem("Exit");
		fileMenu.add(af);
		fileMenu.add(ae);
		
		menuBar.add(fileMenu);
		
		return menuBar;
	}
	
	private static void openLogin() {
		new UILoginRegister(frame,false);
	}
	
	private static void openRegistry() {
		new UILoginRegister(frame,true);
	}
	
	private static void makeLowerActionPanelButtons(JPanel container) {
		JButton openLoginButton = new JButton("Login");
		JButton openRegButton = new JButton("Register");
		
		openLoginButton.addActionListener(e -> openLogin());
		openRegButton.addActionListener(e -> openRegistry());
		
		container.add(openLoginButton);
		container.add(openRegButton);
		
	}
	


}