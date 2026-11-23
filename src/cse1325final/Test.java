package cse1325final;

import java.util.ArrayList;

public class Test {
    private int id;
    private String name;
    private ArrayList<TestQuestion> questions;
    private ArrayList<TestRecord> results;
    private int authorId;

    public Test(int id, String name, int authorId) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        questions = new ArrayList<>();
        results = new ArrayList<>();
    }

    // Test ID
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Test name
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Questions
    public ArrayList<TestQuestion> getQuestions() {
        return new ArrayList<>(questions);
    }

    public void setQuestions(ArrayList<TestQuestion> questions) {
        this.questions = questions != null
                ? new ArrayList<>(questions)
                : new ArrayList<>();
    }

    public void addQuestion(TestQuestion question) {
        if (question != null) {
            questions.add(question);
        }
    }

    public void removeQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            questions.remove(index);
        }
    }

    public TestQuestion getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        }

        return null;
    }

    public int getQuestionCount() {
        return questions.size();
    }

    // Student results
    public ArrayList<TestRecord> getResults() {
        return new ArrayList<>(results);
    }

    public void setResults(ArrayList<TestRecord> results) {
        this.results = results != null
                ? new ArrayList<>(results)
                : new ArrayList<>();
    }

    public void addResult(TestRecord record) {
        if (record != null) {
            results.add(record);
        }
    }

    public void removeResult(TestRecord record) {
        results.remove(record);
    }

    // Author
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
}
