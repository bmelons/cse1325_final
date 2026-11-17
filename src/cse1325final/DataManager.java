package cse1325final;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

public class DataManager {
	private static String filePath = "~/Documents/Test/usr.dat";
	private static FileWriter writer;
	
	// considering making the following 2 variables static
	private GenLL<User> UsersLL;
	private GenLL<Test> TestsLL;
	
	public DataManager() {
		UsersLL = new GenLL<>();
		TestsLL = new GenLL<>();
		this.importUsers();
		this.importTests();
		return;
	}
	
	public void addUser(User user) {
		if (UsersLL == null) {
			return;
		}
		UsersLL.add(user);
	}
	
	public User getUser(String name) {
		
		return UsersLL.search(
				user -> user.getUsername().equals(name) 
				);
	}
	
	public User getUser(int id) {
		return UsersLL.search(
				user -> user.getID()==id
				);
	}
	
	public Test getTest(String name) {
		
		return TestsLL.search(
				test -> test.getName().equals(name)
				);
		
	}
	public Test getTest(int id) {
		//stub
		return TestsLL.search(
				test -> test.getId()==id
				);
	}

	
	//TODO: how do we want to serialize/deserialize?
	// maybe a static factory inside the user and test classes..? idk at the moment
	
	private void importUsers() { // was readUsersData() in uml
		// should read and store user data
		
	}
	
	private void importTests() { // was readTestData()
		//sssdsdsd
	}
	
	public void saveUserData() {
		//stub
	} 
	
	public void saveTestData() {
		
	}
	
}
