package cse1325final;

public class Backend {
	// lets hash our passwordsss
	private User currentUser;
	
	public boolean login(String username, String password) {
		//stub
		// This should return the RESULT of the login attempt
		// if it succeeds return true, maybe turn into a enum or string for a more dynamic result
		return false;
	}
	
	public User register(String username, String password) {
		//stub
		// core registry function, result is by default a student, permission level is defined by related functions
		return null;
	}
	
	public User register(String username, String password, String authCode) {
		//stub
		if (true) // TODO: If auth code is invalid, return null	
		{
			return null;
		}
		User newUser = null;
		
		// TODO: set permission code
		
		// this is the PROFESSOR version of the register function, since they have to give an auth code to register
		return null;
	}
	
	public Test createTest(String name) {
		// stub
		return null;
	}
	
	public void submitTest(String name) {
		//stub
		return;
	}
	
	public void getAvailableTests() {
		// stub
	}
	
	public TestRecord[] getCompletedTests() {
		// stub
		return null;
	}
	
	public float calculateScore(TestRecord record) {
		//stub
		return 0.0f;
	}

}
