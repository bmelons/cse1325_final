package cse1325final;

import java.util.ArrayList;

public class User {
    // User information
    private int id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;

    // Account permissions
    private PermissionLevel permissionLevel;

    // IDs of tests completed by this user
    private ArrayList<Integer> testsTaken;

    public User(String first, String last, String user, String pass) {
        id = 0;
        firstName = first;
        lastName = last;
        username = user;
        password = pass;
        permissionLevel = PermissionLevel.NONE;
        testsTaken = new ArrayList<>();
    }

    // ID
    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    // Name
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String first) {
        firstName = first;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String last) {
        lastName = last;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    // Account credentials
    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        username = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    // Permissions
    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(PermissionLevel level) {
        permissionLevel = level;
    }

    // Test history
    public ArrayList<Integer> getTestsTaken() {
		// new ArrayList to prevent accidental modification of the existing one
        return new ArrayList<>(testsTaken);
    }

    public void addTestTaken(int testID) {
        if (!testsTaken.contains(testID)) {
            testsTaken.add(testID);
        }
    }

    public void removeTestTaken(int testID) {
        testsTaken.remove(Integer.valueOf(testID));
    }

    public boolean hasTakenTest(int testID) {
        return testsTaken.contains(testID);
    }
}
