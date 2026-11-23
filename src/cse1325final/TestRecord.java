package cse1325final;

import java.util.ArrayList;

public class TestRecord {
    private int userId;
    private int testId;
    private ArrayList<Object> answers;
    private double score; // a percentage; from 0.0 to 1.0

    public TestRecord() {
        userId = 0;
        testId = 0;
        answers = new ArrayList<>();
        score = 0.0;
    }

    public TestRecord(int userId, int testId, ArrayList<Object> answers, double score) {
        this.userId = userId;
        this.testId = testId;
        this.answers = answers != null
			? new ArrayList<>(answers)
			: new ArrayList<>();
        this.score = score;
    }

    // Student ID
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // Test ID
    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    // Submitted answers
    public ArrayList<Object> getAnswers() {
        return new ArrayList<>(answers);
    }

    public void setAnswers(ArrayList<Object> answers) {
        this.answers = answers != null
                ? new ArrayList<>(answers)
                : new ArrayList<>();
    }

    // Score
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
