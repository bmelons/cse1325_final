package cse1325final;

public class TestQuestion {
    private String question = "";
    private QuestionType questionType;
    private int answer; // index if question type is multiple-choice, numeric if the question type is numeric
    private String options [] ;

    public TestQuestion(String question, QuestionType questionType, int answer, String[] options) {
        this.question = question;
        this.questionType = questionType;
        this.answer = answer;
        this.options = options;
    }

    public String getQuestion() {
        return this.question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public String[] getOptions() {
        return this.options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public boolean equals(TestQuestion other) {
        return  question.toLowerCase().equals(other.question.toLowerCase()) &&
                questionType == other.questionType &&
                answer == other.answer &&
                options.equals(other.options);
    }
}
