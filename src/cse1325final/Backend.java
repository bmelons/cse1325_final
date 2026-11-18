package cse1325final;

public class Backend {
	private static DataManager dataManager = new DataManager();
	
	// account string length restrictions are exclusive (min < length < max)
	private static final int PASSWORD_MINIMUM_LENGTH = 3;
	private static final int PASSWORD_MAXIMUM_LENGTH = 20;
	
	private static final int USERNAME_MINIMUM_LENGTH = 3;
	private static final int USERNAME_MAXIMUM_LENGTH = 20;
	
	// TODO: consider extracting these to make this class easier to read
	// EXCEPTIONS
	public static final class PasswordLengthException extends Exception {
		public PasswordLengthException() {
			super("Illegal Password Length: must be greater than " + PASSWORD_MINIMUM_LENGTH + " characters and less than " + PASSWORD_MAXIMUM_LENGTH + " characters");
		}
	}
	
	public static final class UsernameLengthException extends Exception {
		public UsernameLengthException() {
			super("Illegal Username Length: must be greater than " + USERNAME_MINIMUM_LENGTH + " characters and less than " + USERNAME_MAXIMUM_LENGTH + " characters");
		}
	}

	public static final class IncompatibleRecordException extends Exception {
		public IncompatibleRecordException() {
			super("The given TestRecord has one or more property that doesn't fit against the given Test");
		}
	}

	public static final class InvalidPermissionsException extends Exception {
		public InvalidPermissionsException(PermissionLevel found, PermissionLevel expected) {
			super("Invalid permissions were given when trying to construct or use this user; found " + found + " but expected " + expected);
		}

		public InvalidPermissionsException(PermissionLevel found = PermissionLevel.NONE) {
			super("Invalid permissions were given; found " + found)
		}
	}
	
	public static final class InvalidAuthorizationException extends Exception {
		public InvalidAuthorizationException() {
			super("Invalid authorization");
		}
	}

	// if more than one instance of this class exists, something is wrong!
	private static User currentUser; // static because only one user can be logged in at a time

	public boolean login(String username, String password) {
		// stub
		// This should return the RESULT of the login attempt
		// if it succeeds return true, maybe make some exceptions
		User user = dataManager.getUser(username);
		if (user == null) {
			return false;
		}

		if (password != user.getPassword()) {
			return false;
		}

		currentUser = user;
		return true;
	}

	public User register(String username, String password , String firstName, String lastName, PermissionLevel permissionLevel = PermissionLevel.STUDENT, String authCode = "")
		throws PasswordLengthException, UsernameLengthException, InvalidPermissionsException, InvalidAuthorizationException
	{
		// call in a protected/try block, errors are returned as exceptions due to the signature requiring a User to be returned rather than a string
		// "} catch(Backend.PasswordLengthException) {"
		
		// hash password here maybe?
		
		if (password.length() < PASSWORD_MINIMUM_LENGTH || 
			password.length() > PASSWORD_MAXIMUM_LENGTH) {
			throw new PasswordLengthException();
		}
		
		if (username.length() < USERNAME_MINIMUM_LENGTH || 
			password.length() > PASSWORD_MAXIMUM_LENGTH) {
			throw new UsernameLengthException();
		}
		
		// permission level should not be set to NONE, but handle it in case it is
		if (permissionLevel == PermissionLevel.NONE) {
			throw new InvalidPermissionsException();
		}

		User newUser = new User(firstName, lastName, username, password);
		newUser.setPermissionLevel();
		switch (permissionLevel) {
			case PermissionLevel.NONE: break; 		// already handled
			case PermissionLevel.STUDENT: break; 	// no action needed

			case PermissionLevel.EDUCATOR: {
				authorizeEducator(user, authCode);
				break;
			}
		}

		newUser.setPermissionLevel(permissionLevel);
		dataManager.addUser(newUser); // solidifies user into the base
		
		return newUser;
	}

	public void authorizeEducator(User user, String authCode) throws InvalidAuthorizationException {
		if (!"<the correct code>".equals(authCode)) {
			throw InvalidAuthorizationException();
		}

		// authorisation OK
	}

	// public User register(String username, String password, String authCode) {
	// 	if (authCode != "the right code") // TODO: connect to any system that allows changing the authcode
	// 	{
	// 		return null;
	// 	}
	// 	User newUser = new User;

	// 	// TODO: set permission code

	// 	// this is the PROFESSOR version of the register function, since they have to
	// 	// give an auth code to register
	// 	return newUser;
	// }

	public Test createTest(String name) {
		// stub
		return null;
	}

	public void submitTest(String name) {
		// stub
		return;
	}

	public void getAvailableTests() {
		// stub
	}

	public TestRecord[] getCompletedTests() {
		// stub
		int count = 0;
		
		// get the total amount by iterating on users
		/* for (User user : [method to get an iterable list of users]) {
			// count the number of completed tests that user has gotten
			count += user.getTestsTaken().length;
		}
		
		*/

		TestRecord[] records = new TestRecord[count];
		// iterate through the above again and assign them by index

		return null;
	}

	public float calculateScore(TestRecord record, Test test) throws IncompatibleRecordException {
		Object[] answers = record.getAnswers();
		return calculateScore(answers, test);
	}

	public float calculateScore(Object[] answers, Test test) throws IncompatibleRecordException {
		TestQuestion[] questions = test.getQuestions();
	
		if (questions.length != answers.length) {
			throw new IncompatibleRecordException();
		}
		
		int correctAnswers = 0;
		
		for (int i = 0; i < questions.length; i++) {
			TestQuestion question = questions[i];
			Object answer = answers[i];
			if (answer.equals(question.getAnswer())) {
				correctAnswers++;
			}
		}
		
		return (float)correctAnswers / (float)questions.length;
	}

}
