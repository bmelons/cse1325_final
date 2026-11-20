package cse1325final;

import java.awt.GridLayout;
import java.lang.reflect.Field;

import javax.swing.*;

public class UILoginRegister extends JDialog {
	private boolean isRegistry;
	private JLabel prompt;
	
	private boolean isEduMode;
	
	// Permission Selection
	private ButtonGroup permissionRadioGroup;
	private JRadioButton educatorRadio;
	private JRadioButton studentRadio;
	private UIFlowPanel authRow;
	
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
		this.isEduMode = false;
		
		
		// layout for this window is a box
		BoxLayout layout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		setLayout(new GridLayout(5,1));
		
		UIFlowPanel roleRow = new UIFlowPanel();
		ButtonGroup radioGroup = new ButtonGroup();
		JRadioButton studentRadio = new JRadioButton();
		JRadioButton eduRadio = new JRadioButton();
		radioGroup.add(studentRadio);
		radioGroup.add(eduRadio);
		studentRadio.setSelected(true);
		
		studentRadio.addActionListener(e -> this.setIsEduMode(false));
		eduRadio.addActionListener(e -> this.setIsEduMode(true));
		
		JLabel studentRLabel = new JLabel("Student");
		JLabel eduRLabel = new JLabel("Educator");
		
		roleRow.add(studentRLabel);
		roleRow.add(studentRadio);
		roleRow.add(eduRLabel);
		roleRow.add(eduRadio);
		
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
		
		this.authRow = new UIFlowPanel();
		this.authLabel = new JLabel("Authentication: ");
		this.authField = new JTextField(16);
		this.authRow.add(this.authLabel);
		this.authRow.add(this.authField);
		
		
		add(roleRow);
		add(unameRow);
		add(pwordRow);
		add(this.authRow);
		this.authRow.setVisible(isEduMode);
		
		
		setSize(400,250);
		setLocationRelativeTo(parentFrame);
		setVisible(true);
//		add(this.usernameLabel);
//		add(this.usernameField);
//		
//		add(passwordField);
	}
	
	public void setIsEduMode(boolean aIsEduMode) {
		
		this.isEduMode = aIsEduMode;
		this.authRow.setVisible(aIsEduMode);
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
