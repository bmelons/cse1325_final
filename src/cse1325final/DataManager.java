package cse1325final;

import java.util.ArrayList;
import java.util.Arrays;
import java.awt.List;
import java.io.*;

public class DataManager {
    // File locations
    private static final String USER_FILE = "users.txt";
    private static final String TEST_FILE = "tests.txt";
    

    // Stored data
    private ArrayList<User> users;
    private ArrayList<Test> tests;
    private boolean isUsingPlaceholders; // prevents saving to files

    public DataManager() {
        users = new ArrayList<>();
        tests = new ArrayList<>();

        importUsers();
        importTests();
        this.isUsingPlaceholders = false;
    }
    
 
    public DataManager(boolean usePlaceholderTestData,boolean usePlaceholderUserData) {
    	this.isUsingPlaceholders = usePlaceholderTestData || usePlaceholderUserData;
    	if (usePlaceholderTestData) {
    		tests = PlaceholderData.getTests();
    	}
    	else {
    		tests = new ArrayList<>();
    		importTests();
		}
    	
    	if (usePlaceholderUserData) {
    		users = PlaceholderData.getUsers();
    	}
    	else {
    		users = new ArrayList<>();
    		importUsers();
		}  
    }
	
	// User management
    public void addUser(User user) {
        if (user == null || getUser(user.getUsername()) != null) {
            return;
        }

        users.add(user);
        saveUserData();
    }

    public User getUser(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public User getUser(int id) {
        for (User user : users) {
            if (user.getID() == id) {
                return user;
            }
        }

        return null;
    }

    public boolean removeUser(int id) {
        User user = getUser(id);

        if (user == null) {
            return false;
        }

        users.remove(user);

        // remove the student's attempts from all tests
        for (Test test : tests) {
            ArrayList<TestRecord> results = test.getResults();

            results.removeIf(record -> record.getUserId() == id);

            test.setResults(results);
        }

        
        saveUserData();
        saveTestData();

        return true;
    }

    public ArrayList<User> getUsers() {
        return new ArrayList<>(users);
    }

	// Test management
    public void addTest(Test test) {
        if (test == null || getTest(test.getId()) != null) {
            return;
        }

        tests.add(test);
        saveTestData();
    }

    public Test getTest(String name) {
        for (Test test : tests) {
            if (test.getName().equals(name)) {
                return test;
            }
        }

        return null;
    }

    public Test getTest(int id) {
        for (Test test : tests) {
            if (test.getId() == id) {
                return test;
            }
        }

        return null;
    }

    public boolean removeTest(int id) {
        Test test = getTest(id);

        if (test == null) {
            return false;
        }

        tests.remove(test);

        // remove test from student histories
        for (User user : users) {
            user.removeTestTaken(id);
        }

        saveUserData();
        saveTestData();

        return true;
    }

    public ArrayList<Test> getTests() {
        return new ArrayList<>(tests);
    }

	// Test record management
    public void addTestRecord(TestRecord record) {
        if (record == null) {
            return;
        }

        Test test = getTest(record.getTestId());

        if (test == null) {
            return;
        }

        User user = getUser(record.getUserId());

        if (user == null) {
            return;
        }

        test.addResult(record);
        user.addTestTaken(record.getTestId());
        System.out.println(user.toString());

        saveUserData();
        saveTestData();
    }

    public ArrayList<TestRecord> getCompletedTests(int userId) {
        ArrayList<TestRecord> completed = new ArrayList<>();

        for (Test test : tests) {
            for (TestRecord record : test.getResults()) {
                if (record.getUserId() == userId) {
                    completed.add(record);
                }
            }
        }

        return completed;
    }

	// File importing
    private void importUsers() {
        File file = new File(USER_FILE);

        if (!file.exists()) {
            return;
        }

		// TODO!
        // (file parsing will be implemented once we
        // establish the complete user file format)
		// consider CSV maybe? it doesn't really have to be complex
    }

    private void importTests() {
        File file = new File(TEST_FILE);

        if (!file.exists()) {
            return;
        }

		// TODO!
    }

	// File exporting
    public void saveUserData() {
        // TODO!
    }

    public void saveTestData() {
        // TODO!
    }
}
