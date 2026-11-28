package cse1325final;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cse1325final.Backend.IncompatibleRecordException;
import cse1325final.Backend.InvalidPermissionsException;
import cse1325final.Backend.InvalidTestException;

import java.awt.*;
import java.util.ArrayList;

public class UITestMenu extends JDialog {
    private JLabel questionLabel;
    private JPanel centerPanel;
    private CardLayout cardLayout;

    // Multiple choice components
    private JPanel mcPanel;
    private ButtonGroup buttonGroup;

    // Number input components
    private JPanel numPanel;
    private JTextField numberField;

    // Bottom navigation components
    private JButton prevButton;
    private JLabel progressLabel;
    private JButton nextButton;
    
    private TestRecord record;
    private Test associatedTest;
    private int currentIndex;

    public UITestMenu(JFrame parent,Test test) {
        super(parent, "Quiz", true);
        this.associatedTest=test;
        prepRecord();
        setSize(450, 320);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout(10, 10));

        // 1. TOP: question text (using html so it wraps)
        questionLabel = new JLabel("<html><body style='width: 380px'>QUESTION</body></html>");
        questionLabel.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));
        add(questionLabel, BorderLayout.NORTH);

        // 2. MIDDLE: cardLayout swaps between thhe two question types
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        // card A: Multiple Choice
        mcPanel = new JPanel();
        mcPanel.setLayout(new BoxLayout(mcPanel, BoxLayout.Y_AXIS));
        buttonGroup = new ButtonGroup();
        

        // card B: Number Input (GridBagLayout centers it nicely)
        numPanel = new JPanel(new GridBagLayout());
        numberField = new JTextField(10);
        numPanel.add(new JLabel("Answer: "));
        numPanel.add(numberField);

        centerPanel.add(mcPanel, "MULTIPLE_CHOICE");
        centerPanel.add(numPanel, "NUMBER");
        add(centerPanel, BorderLayout.CENTER);

        // 3. BOTTOM: Previous | Label | Next
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 15, 15));

        prevButton = new JButton("Previous");
        progressLabel = new JLabel("Question X of Y", SwingConstants.CENTER);
        nextButton = new JButton("Next");

        bottomPanel.add(prevButton, BorderLayout.WEST);
        bottomPanel.add(progressLabel, BorderLayout.CENTER);
        bottomPanel.add(nextButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
        setLocationRelativeTo(parent);
        
        prevButton.addActionListener(button -> this.previous());
        nextButton.addActionListener(button -> this.next());
        
        numberField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { onNumberChanged(); }
            @Override
            public void removeUpdate(DocumentEvent e) { onNumberChanged(); }
            @Override
            public void changedUpdate(DocumentEvent e) { onNumberChanged(); }
        });
        
        this.setIndex(0);
    }
    
    private void update(int qIndex) {
    	this.clearQuestion();
    	TestQuestion question = fetchQuestion(qIndex);
    	questionLabel.setText("<html><body style='width: 380px'>" + question.getQuestion() + "</body></html>");
    	progressLabel.setText("Question: " + (qIndex+1) + " of " + this.associatedTest.getQuestionCount() );
    	
    	showQuestionType(question.getQuestionType());
    	
    	// set as the current/default answer from the record
    	Integer savedAnswer = record.getAnswers().get(qIndex);

        if (question.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
            if (question.getOptions() != null) {
                for (int i = 0; i < question.getOptions().size(); i++) {
                    String option = question.getOptions().get(i);
                    JRadioButton radio = this.addChoice(option, qIndex);
                    if (savedAnswer != null && savedAnswer == i) {
                        radio.setSelected(true);
                    }
                }
            }
        } else {
            // Load saved number answer (avoid triggering save logic while populating)
            numberField.setText(savedAnswer != null ? savedAnswer.toString() : "");
        }
        
        mcPanel.revalidate();
        mcPanel.repaint();
    	updateNavButtons();
    	
    }
    
    private void prepRecord() {
        ArrayList<Integer> emptyAnswers = new ArrayList<>();
        ArrayList<TestQuestion> questions = this.associatedTest.getQuestions();
        for (int i = 0; i < this.associatedTest.getQuestionCount(); i++) {
            // use -1 for mc so none are selected
        	// use 0 for numeric because -1 is a weird number to start at
        	int preset = questions.get(i).getQuestionType() == QuestionType.MULTIPLE_CHOICE ? -1 : 0;
            emptyAnswers.add( preset );
        }
        int userId = -1;
        if (Frontend.backend.getCurrentUser() != null) {
        	userId = Frontend.backend.getCurrentUser().getID();
        	System.out.println("id fetched." + userId);
        }
        record = new TestRecord(userId, this.associatedTest.getId(), emptyAnswers, 0.0f);
    }
    
    public void updateNavButtons() {
    	prevButton.setEnabled(this.currentIndex > 0);
        nextButton.setText(this.currentIndex >= this.associatedTest.getQuestionCount() - 1 ? "Submit" : "Next");

        TestQuestion currentQ = fetchQuestion(this.currentIndex);
        if (currentQ.getQuestionType() == QuestionType.MULTIPLE_CHOICE) {
            // dont let the user progress if a mc isnt answered
            nextButton.setEnabled(buttonGroup.getSelection() != null);
        } else {
            validateNumberInput();
        }
    }
    
    private TestQuestion fetchQuestion(int index) {
    	return this.associatedTest.getQuestion(index);
    }
    
    private JRadioButton addChoice(String opt, int qIndex) {
        int index = buttonGroup.getButtonCount();
        JRadioButton rb = new JRadioButton(opt);
        rb.addActionListener(e -> {
            record.setAnswer(qIndex, index);
            updateNavButtons(); 
        });
        buttonGroup.add(rb);
        mcPanel.add(rb);
        return rb;
    }
    
    private void onNumberChanged() {
        // Only validate/save if we are actually viewing a NUMBER question
        if (fetchQuestion(this.currentIndex).getQuestionType() != QuestionType.NUMBER) {
            return;
        }
        String text = numberField.getText().trim();
        try {
            int val = Integer.parseInt(text);
            record.setAnswer(this.currentIndex, val);
            nextButton.setEnabled(true);
        } catch (NumberFormatException e) {
            nextButton.setEnabled(false);
        }
    }

    private void validateNumberInput() {
        String text = numberField.getText().trim();
        if (text.isEmpty()) {
            nextButton.setEnabled(false);
            return;
        }
        try {
            Integer.parseInt(text);
            nextButton.setEnabled(true);
        } catch (NumberFormatException e) {
            nextButton.setEnabled(false);
        }
    }
    
    private void clearQuestion() {
        // clear the question text
        questionLabel.setText("");

        // clear multiple-choice radio buttons (both UI and ButtonGroup)
        mcPanel.removeAll();
        buttonGroup = new ButtonGroup();
        // these fix some scaling and positioning issues
        mcPanel.revalidate();
        mcPanel.repaint();

        // clear the number input field
        numberField.setText("");
    }

    // helper method to switch what shows in the middle when loading a question
    public void showQuestionType(QuestionType type) {
        if (type == QuestionType.MULTIPLE_CHOICE) {
            cardLayout.show(centerPanel, "MULTIPLE_CHOICE");
        } else {
            cardLayout.show(centerPanel, "NUMBER");
        }
    }
    
    public void next() {
    	if (this.currentIndex >= this.associatedTest.getQuestionCount()-1) {
    		System.out.println("submitting test placeholder");
    		try {
				Frontend.backend.submitTest(record);
			} catch (InvalidPermissionsException | InvalidTestException | IncompatibleRecordException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		dispose();
    		return;
    	}
    	setIndex(this.currentIndex + 1);
    }
    
    public void previous() {
        if (this.currentIndex > 0) {
            setIndex(this.currentIndex - 1);
        }
    }
    
    public void setIndex(int aIndex) {
    	this.currentIndex = aIndex;
    	update(aIndex);
    }
}