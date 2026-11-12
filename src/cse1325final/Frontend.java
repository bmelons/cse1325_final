package cse1325final;

import java.awt.BorderLayout;

import javax.swing.*;

public class Frontend {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Intializing"); // makin' sure this method even runs before any real code is written, ->
		// -> reloaded the project freshly cloned into a new workspace, $git diff shows
		// no differences so i think we're good
		
		JFrame frame = makeMainFrame();
		
		
		frame.setVisible(true);
	}
	
	private static JFrame makeMainFrame() {
		// internal helper for readability
		
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
		frame.setSize(600,700);
		frame.setLayout(new BorderLayout(10, 10));
		
		return frame;
	}

}