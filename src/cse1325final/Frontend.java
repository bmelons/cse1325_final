package cse1325final;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class Frontend {
	
	private enum MainMenuStates {
		TESTS,
		SCORES
	}
	
	private static final String[] TEST_COLUMNS = {"Test Name","Author", "Question #"};
	private static final String[] SCORE_COLUMNS = {"Test Name","Score"};
	private static JFrame frame;
	public static Backend backend;
	
	private static JLabel loginStatus;
	private static UIList list;
	
	private static ArrayList<Test> listedTests;
	private static MainMenuStates state;
	
	private static ButtonGroup navTab = new ButtonGroup();
	private static JToggleButton testsMenuButton;
	private static JToggleButton scoresMenuButton;
	
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
		
		JPanel navbarPanel = new UIFlowPanel();
		navTab = new ButtonGroup();
		testsMenuButton = new JToggleButton("Tests");
		scoresMenuButton = new JToggleButton("Scores");
		testsMenuButton.addActionListener(e -> setState(MainMenuStates.TESTS));
		scoresMenuButton.addActionListener(e -> setState(MainMenuStates.SCORES));
		navTab.add(testsMenuButton);
		navTab.add(scoresMenuButton);
		navbarPanel.add(testsMenuButton);
		navbarPanel.add(scoresMenuButton);
		scoresMenuButton.setEnabled(false);
		
		JPanel actionPanel = new UIFlowPanel();
		makeLowerActionPanelButtons(actionPanel);
		
		loginStatus = new JLabel("Guest Account");
		actionPanel.add(loginStatus);
		
//		JButton aabb = new JButton("testing test");
//		aabb.addActionListener(e-> openUiTestMenu());
//		actionPanel.add(aabb);
		
		
		list = new UIList("",TEST_COLUMNS); // TODO: Think about how we want to handle swapping out lists, refresh the existing one or make a new instance?
		
		frame.add(navbarPanel,BorderLayout.PAGE_START);
		frame.add(list, BorderLayout.CENTER);
		frame.add(actionPanel,BorderLayout.PAGE_END);
		
		testsMenuButton.setSelected(true);
		frame.setVisible(true);
		setState(MainMenuStates.TESTS);
		
		
	}
	
	public static void populateTests() {
		ArrayList<Test> tests = backend.getAvailableTests();
		listedTests = tests;
		list.setColumns(TEST_COLUMNS);
		for (Test test : tests) {
			String authorName = backend.getAuthorName(test.getAuthorId());
			Integer questionCount = test.getQuestions().size();
			Object[] data = {test.getName(), authorName, questionCount.toString()};
			System.out.println(data);
			list.addRow(data);
		}
	}
	
	public static void populateScore() {
		System.out.println("populate scores");
		if (backend.getCurrentUser() == null) return;
//		System.out.println(backend.getCurrentUser().toString());
		User user = backend.getCurrentUser();
		ArrayList<Integer> recordIds = user.getTestsTaken();
		ArrayList<Test> testsTaken = new ArrayList<Test>();
		for (Integer id : recordIds) {
			System.out.println("id record thing " + id);
			Test fetchedTest = backend.getTest(id);
			testsTaken.add(fetchedTest);
		}
		list.setColumns(SCORE_COLUMNS);
		for (Test test : testsTaken) {
			TestRecord crecord = backend.getRecord(test, user.getID());
			Object[] data = {test.getName(),crecord.getScore()};
			list.addRow(data);
		}
		
		
	}
	
	public static UITestMenu openUiTestMenu() {
		if (!Backend.isLoggedIn()) {
			return null;
		}
		UITestMenu testing = new UITestMenu(frame,backend.getAvailableTests().get(0));
		testing.setVisible(true);
		return testing;
	}
	
	public static UITestMenu openTest(Test test) {
		if (!Backend.isLoggedIn()) {
			return null;
		}
		UITestMenu testing = new UITestMenu(frame,test);
		testing.setVisible(true);
		return testing;
		
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
//		af = new JMenuItem("Save");
//		fileMenu.add(af);
		ae = new JMenuItem("Exit");
		fileMenu.add(ae);
		
		ae.addActionListener(e -> frame.dispose() );
		
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
		JButton temp_openTestMenu = new JButton("Testing Menu");
		JButton openLoginButton = new JButton("Login");
		JButton openRegButton = new JButton("Register");
		
		openLoginButton.addActionListener(e -> openLogin());
		openRegButton.addActionListener(e -> openRegistry());
		
		container.add(openLoginButton);
		container.add(openRegButton);
		
	}
	
	public static void updateLoginStatus() {
		loginStatus.setText("Welcome, " + backend.getCurrentUser().getFullName() + "!" );
		scoresMenuButton.setEnabled(true);
		
	}

	public static ArrayList<Test> getListedTests() {
		return listedTests;
	}
	
	public static void setState(MainMenuStates aState) {
		state = aState;
		String newTitle = switch (state) {
		case TESTS: {
			populateTests();
			yield "Available Tests";
			}
		case SCORES: {
			populateScore();
			yield "Your scores";
			}
		default: {yield "";}
		};
		
		
	}
	
	


}