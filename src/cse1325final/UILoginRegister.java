package cse1325final;

import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.lang.reflect.Field;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import cse1325final.Backend.DuplicateUsernameException;
import cse1325final.Backend.InvalidAuthorizationException;
import cse1325final.Backend.InvalidPermissionsException;
import cse1325final.Backend.PasswordLengthException;
import cse1325final.Backend.UsernameLengthException;

public class UILoginRegister extends JDialog {
	private boolean isRegistry;
	private JLabel prompt;
	
	private static final int ROW_COUNT = 7;
	private static final Dimension WINDOW_SIZE = new Dimension(500,350);
	
	private boolean isEduMode;
	
	// Permission Selection
	private ButtonGroup permissionRadioGroup;
	private JRadioButton educatorRadio;
	private JRadioButton studentRadio;
	private UIFlowPanel namesRow;
	private UIFlowPanel authRow;
	private UIFlowPanel actionRow;
	private JPanel[] rows;
	
	// data entry
	JLabel firstNameLabel;
	JTextField firstNameField;
	
	JLabel lastNameLabel;
	JTextField lastNameField;
	
	JLabel usernameLabel;
	JTextField usernameField;
	
	JLabel passwordLabel;
	JTextField passwordField;
	
	JLabel authLabel;
	JTextField authField;
	
	JLabel feedbackLabel;
	
	JButton confirmButton;
	JButton exitButton;
	
	public UILoginRegister(JFrame parentFrame, boolean aisRegistry) {
		super(parentFrame,makeTitle(aisRegistry),true);
		
		
		
		// layout for this window is a box
		JPanel content = new JPanel(new BorderLayout(10,10));
		content.setBorder(new EmptyBorder(10, 20, 10, 20));
		this.setContentPane(content);
		BoxLayout layout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
		
		setLayout(new GridLayout(ROW_COUNT,1));
		
		rows = new JPanel[ROW_COUNT];
		for (int i = 0; i<ROW_COUNT; i++) {
			rows[i] = new JPanel();
			add(rows[i]);
		}
		
		// i will find a way to make the following code less of a big wall 
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
		
		this.namesRow = new UIFlowPanel();
		this.firstNameLabel = new JLabel("First Name: ");
		this.firstNameField = new JTextField(10);
		this.lastNameLabel = new JLabel("Last Name: ");
		this.lastNameField = new JTextField(10);
		this.setIsRegistry(aisRegistry);
		this.isEduMode = false;

		this.namesRow.add(this.firstNameLabel);
		this.namesRow.add(this.firstNameField);
		this.namesRow.add(this.lastNameLabel);
		this.namesRow.add(this.lastNameField);
		
		
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
		
		this.feedbackLabel = new JLabel();
		
		this.authRow = new UIFlowPanel();
		this.authLabel = new JLabel("Authentication: ");
		this.authField = new JTextField(16);
		this.authRow.add(this.authLabel);
		this.authRow.add(this.authField);
		
		
		this.actionRow = new UIFlowPanel();
		this.confirmButton = new JButton("Confirm");
		this.exitButton = new JButton("Exit");
		this.confirmButton.addActionListener(e -> this.submit());
		this.exitButton.addActionListener(e -> this.dispose());
		this.actionRow.add(this.confirmButton);
		this.actionRow.add(this.exitButton);
		
		
		
		
		rows[0].add(roleRow);
		rows[1].add(namesRow);
		rows[2].add(unameRow);
		rows[3].add(pwordRow);
		rows[4].add(this.authRow);
		rows[5].add(this.feedbackLabel);
		rows[6].add(this.actionRow);
		this.authRow.setVisible(isEduMode);
		
		
		
		setSize(WINDOW_SIZE);
		setMinimumSize(WINDOW_SIZE);
		setMaximumSize(WINDOW_SIZE);
		setLocationRelativeTo(parentFrame);
		setVisible(true);
		
		usernameField.grabFocus(); // intended to automatically has the user select the text box
		
//		add(this.usernameLabel);
//		add(this.usernameField);
//		
//		add(passwordField);
	}
	
	public void setIsEduMode(boolean aIsEduMode) {
		
		this.isEduMode = aIsEduMode;
		this.authRow.setVisible(aIsEduMode && this.isRegistry);
	}
	
	public void setIsRegistry(boolean aisRegistry) {
		this.isRegistry = aisRegistry;
		this.setTitle(makeTitle(aisRegistry));
		this.namesRow.setVisible(aisRegistry);
	}
	
	public void register() {
		PermissionLevel perms = this.getCurrentLevel();
		User newUser = null;
		System.out.println("making new user");
		try {
			newUser = Frontend.backend.register(
					this.usernameField.getText(), 
					this.passwordField.getText(), 
					this.firstNameField.getText(), 
					this.lastNameField.getText(), 
					perms, 
					this.authField.getText());
		} catch (PasswordLengthException | UsernameLengthException | DuplicateUsernameException | InvalidPermissionsException
				| IllegalArgumentException | InvalidAuthorizationException e) {
			e.printStackTrace();
			this.displayFeedback(e.getMessage(), FeedbackType.ERROR);
		}
		if (newUser != null) {
			this.displayFeedback(newUser.getFullName() + " is registered", FeedbackType.INFO);
		}
	}
	
	public void login() {
		if (Frontend.backend.login(this.usernameField.getText(), this.passwordField.getText())) {
			this.dispose();
		}
		else {
			this.displayFeedback("Either username or password is wrong", FeedbackType.ERROR);
		}
	}
	
	public void submit() {
		System.out.println("submitting");
		if (this.isRegistry) register();
		else login();
	}
	
	public PermissionLevel getCurrentLevel() {
		if (this.isEduMode) {
			return PermissionLevel.EDUCATOR;
		}
		else {
			return PermissionLevel.STUDENT;
		}
	}
	
	public void displayFeedback(String content, FeedbackType fType) {
		Color color = switch (fType) {
		case INFO -> Color.blue;
		case ERROR -> Color.red;
		};
		this.feedbackLabel.setText("<html><body style='width: 300px'>"+content+"</body></html>");
		this.feedbackLabel.setForeground(color);
	}
	
	public void emptyFeedback() {
		this.feedbackLabel.setText("");
	}
	
	private static String makeTitle(boolean aisRegistry) {
		if (aisRegistry) {
			return "Register";
		}
		else {
			return "Login";
		}
	}
	
	public enum FeedbackType {
		INFO,
		ERROR
	}

}
