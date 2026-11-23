package cse1325final;

import java.util.ArrayList;

public class TestQuestion {
    private String question;
    private QuestionType questionType;
    private int answer; // the index of the right answer if multiple-choice, and numeric if numeric
    private ArrayList<String> options;

    public TestQuestion(String question, QuestionType questionType, int answer, ArrayList<String> options) {
        this.question = question;
        this.questionType = questionType;
        this.answer = answer;

		this.options = options != null
			? new ArrayList<>(options)
			: new ArrayList<>();
    }

    // Question
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    // Question type
    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    // Correct answer
    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    // Answer options
    public ArrayList<String> getOptions() {
        return new ArrayList<>(options);
    }

    public void setOptions(ArrayList<String> options) {
        if (options == null) {
            this.options = new ArrayList<>();
        } else {
            this.options = new ArrayList<>(options);
        }
    }

    public void addOption(String option) {
        options.add(option);
    }

    public void removeOption(int index) {
        if (index >= 0 && index < options.size()) {
            options.remove(index);
        }
    }

    // Check submitted answer
    public boolean isCorrect(int submittedAnswer) {
        return submittedAnswer == answer;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof TestQuestion)) {
            return false;
        }

        TestQuestion other = (TestQuestion) obj;

        return question.equalsIgnoreCase(other.question)
                && questionType == other.questionType
                && answer == other.answer
                && options.equals(other.options);
    }
}
