package cse1325final;


public class TestRecord {
	
	private int userId;
	private Object[] answers; // restricted to Integer (not int, since its primitive), and String
	// once we implement some back-end, maybe migrate to TestRecord<T>
	
	public TestRecord() {
		this.userId = 0;
		this.answers = new Object[0];
	}
	
	public TestRecord(int auserId,Object[] aAnswers) {
		this.setUserId(auserId);
		this.setAnswers(aAnswers);
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Object[] getAnswers() {
		return answers;
	}
	public void setAnswers(Object[] aAnswers) {
		// restrict to known answer types
		if (aAnswers == null) return;
		
		this.answers = aAnswers;
	}
	
}
