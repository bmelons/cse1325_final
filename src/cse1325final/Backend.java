package cse1325final;

import java.util.ArrayList;

public class Backend {
    private static final DataManager dataManager = new DataManager();

    // Only one user can be logged in at a time
    private static User currentUser;

    // Account restrictions
    private static final int PASSWORD_MINIMUM_LENGTH = 3;
    private static final int PASSWORD_MAXIMUM_LENGTH = 20;

    private static final int USERNAME_MINIMUM_LENGTH = 3;
    private static final int USERNAME_MAXIMUM_LENGTH = 20;

    // Registration authorization codes
	// (we can probably just read these from a file. like "auth.txt" or something)
	// TODO!
    private static final String EDUCATOR_AUTH_CODE = "TODO_EDUCATOR";
    private static final String ADMIN_AUTH_CODE = "TODO_ADMIN";

	// Exceptions
    public static final class PasswordLengthException extends Exception {
        public PasswordLengthException() {
            super("Password must be greater than "
                    + PASSWORD_MINIMUM_LENGTH
                    + " characters and less than "
                    + PASSWORD_MAXIMUM_LENGTH + " characters.");
        }
    }

    public static final class UsernameLengthException extends Exception {
        public UsernameLengthException() {
            super("Username must be greater than "
                    + USERNAME_MINIMUM_LENGTH
                    + " characters and less than "
                    + USERNAME_MAXIMUM_LENGTH + " characters.");
        }
    }

    public static final class IncompatibleRecordException extends Exception {
        public IncompatibleRecordException() {
            super("The TestRecord does not match the given Test.");
        }
    }

    public static final class InvalidPermissionsException extends Exception {
        public InvalidPermissionsException(PermissionLevel found, PermissionLevel expected) {
            super("Invalid permissions. Found " + found + " but expected " + expected + ".");
        }

        public InvalidPermissionsException(PermissionLevel found) {
            super("Invalid permissions: " + found);
        }

        public InvalidPermissionsException() {
            this(PermissionLevel.NONE);
        }
    }

    public static final class InvalidAuthorizationException extends Exception {
        public InvalidAuthorizationException() {
            super("Invalid authorization code.");
        }
    }

    public static final class DuplicateUsernameException extends Exception {
        public DuplicateUsernameException() {
            super("That username is already in use.");
        }
    }

    public static final class InvalidTestException extends Exception {
        public InvalidTestException(String message) {
            super(message);
        }
    }

	// Authentication
    public boolean login(String username, String password) {

        User user = dataManager.getUser(username);

        if (user == null) {
            return false;
        }

        if (!password.equals(user.getPassword())) {
            return false;
        }

        if (user.getPermissionLevel() == PermissionLevel.NONE) {
            return false;
        }

        currentUser = user;
        return true;
    }

    public void logout() {
        currentUser = null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
	
	// Registration
    public User register(
            String username,
            String password,
            String firstName,
            String lastName,
            PermissionLevel permissionLevel,
            String authCode)
            throws PasswordLengthException,
                   UsernameLengthException,
                   InvalidPermissionsException,
                   InvalidAuthorizationException,
                   DuplicateUsernameException
	{
        if (username == null || username.length() <= USERNAME_MINIMUM_LENGTH || username.length() >= USERNAME_MAXIMUM_LENGTH) {
            throw new UsernameLengthException();
        }

        if (password == null || password.length() <= PASSWORD_MINIMUM_LENGTH || password.length() >= PASSWORD_MAXIMUM_LENGTH) {
            throw new PasswordLengthException();
        }

        if (firstName == null || firstName.trim().isEmpty() || lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("First and last names cannot be empty.");
        }

        if (dataManager.getUser(username) != null) {
            throw new DuplicateUsernameException();
        }

        if (permissionLevel == null) {
            permissionLevel = PermissionLevel.STUDENT;
        }

        if (permissionLevel == PermissionLevel.NONE) {
            throw new InvalidPermissionsException();
        }

        // Only administrators can create educator accounts
        if (permissionLevel == PermissionLevel.EDUCATOR) {
            requirePermission(PermissionLevel.ADMINISTRATOR);
            authorizeEducator(authCode);
        }

        // Administrator accounts cannot be self-registered
        if (permissionLevel == PermissionLevel.ADMINISTRATOR) {
            throw new InvalidPermissionsException(PermissionLevel.ADMINISTRATOR);
        }

        User newUser = new User(firstName.trim(), lastName.trim(), username, password);

        newUser.setPermissionLevel(permissionLevel);
        newUser.setID(generateUserID());

        dataManager.addUser(newUser);

        return newUser;
    }

    public void authorizeEducator(String authCode) throws InvalidAuthorizationException {
        if (authCode == null || !ADMIN_AUTH_CODE.equals(authCode)) {
            throw new InvalidAuthorizationException();
        }
    }

	// Permissions
    private void requireLogin() throws InvalidPermissionsException {
        if (currentUser == null) {
            throw new InvalidPermissionsException();
        }
    }

    private void requirePermission(PermissionLevel expected) throws InvalidPermissionsException {
        requireLogin();

        if (currentUser.getPermissionLevel() != expected && currentUser.getPermissionLevel() != PermissionLevel.ADMINISTRATOR) {
            throw new InvalidPermissionsException(currentUser.getPermissionLevel(), expected);
        }
    }

	// ID generation
    private int generateUserID() {
        int highestID = 0;

        for (User user : dataManager.getUsers()) {
            if (user.getID() > highestID) {
                highestID = user.getID();
            }
        }

        return highestID + 1;
    }

    private int generateTestID() {
        int highestID = 0;

        for (Test test : dataManager.getTests()) {
            if (test.getId() > highestID) {
                highestID = test.getId();
            }
        }

        return highestID + 1;
    }
	
	// Test creation
    public Test createTest(String name) throws InvalidPermissionsException, InvalidTestException {
        requirePermission(PermissionLevel.EDUCATOR);

        if (name == null || name.trim().isEmpty()) {
            throw new InvalidTestException("Test name cannot be empty.");
        }

        Test test = new Test(generateTestID(), name.trim(), currentUser.getID());

        dataManager.addTest(test);

        return test;
    }

	// Question management
    public void addQuestion(int testID, TestQuestion question) throws InvalidPermissionsException, InvalidTestException {
        requirePermission(PermissionLevel.EDUCATOR);

        Test test = dataManager.getTest(testID);

        if (test == null) {
            throw new InvalidTestException("Test not found.");
        }

        if (test.getAuthorId() != currentUser.getID() && currentUser.getPermissionLevel() != PermissionLevel.ADMINISTRATOR) {
            throw new InvalidPermissionsException(currentUser.getPermissionLevel(), PermissionLevel.EDUCATOR);
        }

        if (question == null) {
            throw new InvalidTestException("Question cannot be empty.");
        }

        test.addQuestion(question);

        recalculateTestScores(test);
        dataManager.saveTestData();
    }

    public void removeQuestion(int testID, int questionIndex) throws InvalidPermissionsException, InvalidTestException {
        requirePermission(PermissionLevel.EDUCATOR);

        Test test = dataManager.getTest(testID);

        if (test == null) {
            throw new InvalidTestException("Test not found.");
        }

        if (test.getAuthorId() != currentUser.getID() && currentUser.getPermissionLevel() != PermissionLevel.ADMINISTRATOR) {
            throw new InvalidPermissionsException(currentUser.getPermissionLevel(), PermissionLevel.EDUCATOR);
        }

        if (questionIndex < 0 || questionIndex >= test.getQuestionCount()) {
            throw new InvalidTestException("Invalid question index.");
        }

        test.removeQuestion(questionIndex);

        recalculateTestScores(test);
        dataManager.saveTestData();
    }

	// Test retrival
    public ArrayList<Test> getAvailableTests() {
        return dataManager.getTests();
    }

    public Test getTest(int testID) {
        return dataManager.getTest(testID);
    }

    public Test getTest(String name) {
        return dataManager.getTest(name);
    }
	
	// Test submission
    public TestRecord submitTest(int testID, ArrayList<Object> answers)
            throws InvalidPermissionsException,
                   InvalidTestException,
                   IncompatibleRecordException
	{
        requirePermission(PermissionLevel.STUDENT);

        Test test = dataManager.getTest(testID);

        if (test == null) {
            throw new InvalidTestException("Test not found.");
        }

        if (answers == null) {
            throw new InvalidTestException("Answers cannot be null.");
        }

        double score = calculateScore(answers, test);

        TestRecord record = new TestRecord(
            currentUser.getID(),
            testID,
            answers,
            score
        );

        dataManager.addTestRecord(record);

        return record;
    }

	// Grading
    public double calculateScore(TestRecord record, Test test) throws IncompatibleRecordException {
        if (record == null) {
            throw new IllegalArgumentException("Test record cannot be null.");
        }

        return calculateScore(record.getAnswers(), test);
    }

    public double calculateScore(ArrayList<Object> answers, Test test) throws IncompatibleRecordException {
        if (test == null || answers == null) {
            throw new IllegalArgumentException("Test and answers cannot be null.");
        }

        ArrayList<TestQuestion> questions = test.getQuestions();

        if (questions.size() != answers.size() || questions.isEmpty()) {
            throw new IncompatibleRecordException();
        }

        int correctAnswers = 0;

        for (int i = 0; i < questions.size(); i++) {
            TestQuestion question = questions.get(i);
            Object submittedAnswer = answers.get(i);

            if ((submittedAnswer instanceof Integer) && question.isCorrect((Integer) submittedAnswer)) {
                correctAnswers++;
            }
        }

        return (double) correctAnswers / questions.size();
    }

	// Test results
    public ArrayList<TestRecord> getCompletedTests() throws InvalidPermissionsException {
        requireLogin();

        if (currentUser.getPermissionLevel() == PermissionLevel.STUDENT) {
            return dataManager.getCompletedTests(currentUser.getID());
        }

        throw new InvalidPermissionsException(currentUser.getPermissionLevel(), PermissionLevel.STUDENT);
    }

	// retroactive grading
    private void recalculateTestScores(Test test) {
        for (TestRecord record : test.getResults()) {
            try {
                double newScore = calculateScore(record, test);
                record.setScore(newScore);
            } catch (IncompatibleRecordException e) {
                // record no longer matches the test
                record.setScore(0.0);
            }
        }
    }
}
