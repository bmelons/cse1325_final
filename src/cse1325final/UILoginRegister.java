package cse1325final;

import java.lang.reflect.Field;

import javax.swing.*;

public class UILoginRegister extends JDialog {
	private boolean isRegistry;
	private JLabel prompt;
	// Permission Selection
	private ButtonGroup permissionRadioGroup;
	private JRadioButton educatorRadio;
	private JRadioButton studentRadio;
	
	// data entry
	JLabel usernameLabel;
	JTextField usernameField;
	
	JLabel passwordLabel;
	JTextField passwordField;
	
	JLabel authLabel;
	JTextField authField;
	
	public UILoginRegister(JFrame parentFrame, boolean aisRegistry) {
		super(parentFrame,makeTitle(aisRegistry),true);
		this.setIsRegistry(aisRegistry);
		
		
		// layout for this window is a box
		BoxLayout layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(layout);
		
		
		UIFlowPanel unameRow = new UIFlowPanel();
		this.usernameLabel = new JLabel("Username: ");
		this.usernameField = new JTextField(16);
		unameRow.add(this.usernameLabel);
		unameRow.add(this.usernameField);
		
		UIFlowPanel pwordRow = new UIFlowPanel();
		this.passwordLabel = new JLabel("Password: ");
		this.passwordField = new JTextField(16);
		pwordRow.add(this.passwordLabel);
		pwordRow.add(this.passwordField);
		
		UIFlowPanel authRow = new UIFlowPanel();
		this.authLabel = new JLabel("Authentication: ");
		this.authField = new JTextField(16);
		authRow.add(this.authLabel);
		authRow.add(this.authField);
		
		
//		add(this.usernameLabel);
//		add(this.usernameField);
//		
//		add(passwordField);
	}
	
	public void setIsRegistry(boolean aisRegistry) {
		this.isRegistry = aisRegistry;
		this.setTitle(makeTitle(aisRegistry));
	}
	
	private static String makeTitle(boolean aisRegistry) {
		if (aisRegistry) {
			return "Register";
		}
		else {
			return "Login";
		}
	}


}
