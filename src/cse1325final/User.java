package cse1325final;

public class User {
	// User info
	private int id;

	private String firstName;
	private String lastName;
	private String username; 
	// username is necessary because of people with the same name, 
	// first, last or both, making searching for it by a single string not practical
	private String password;
	private int permissionLevel;
	// A list of test IDs taken by the user
	private int testsTaken[];

	public User(String first, String last, String user, String pass) {
		id = 0;
		firstName = first;
		lastName = last;
		username = user;
		password = pass;
		permissionLevel = 0;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

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

	public int getPermissionLevel() {
		return permissionLevel;
	}

	public void setPermissionLevel(int level) {
		permissionLevel = level;
	}

	public int[] getTestsTaken() {
		return testsTaken;
	}

	public void setTestsTaken(int[] tests) {
		testsTaken = tests;
	}
}
