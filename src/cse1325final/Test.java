package cse1325final;

public class Test {
	private int id;
	private String name;
	private TestQuestion[] questions;
	private TestRecord[] results;
	private int authorId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public TestQuestion[] getQuestions() {
		return questions;
	}
	public void setQuestions(TestQuestion[] questions) {
		this.questions = questions;
	}
	public TestRecord[] getResults() {
		return results;
	}
	public void setResults(TestRecord[] results) {
		this.results = results;
	}
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	
}
