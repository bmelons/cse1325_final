package cse1325final;

import java.util.ArrayList;
import java.util.Arrays;

public class PlaceholderData {
	// ugly temporary class, for placeholder data to work on the frontend with
	
	public static ArrayList<Test> getTests() {
		
		Test test1 = new Test(0,"yoga for hobos quiz 3",0);
		Test test2 = new Test(1, "i am sick of this", 0);
		
		test1.addQuestion(new TestQuestion("1+2 equals?", QuestionType.MULTIPLE_CHOICE, 1,new ArrayList<>(Arrays.asList("1","3","-1"))));
		test1.addQuestion(new TestQuestion("5-3 equals?", QuestionType.NUMBER, 2));
		test1.addQuestion(new TestQuestion("3+2 equals?", QuestionType.MULTIPLE_CHOICE, 0,new ArrayList<>(Arrays.asList("5","1","6"))));
		
		test2.addQuestion(new TestQuestion("do i want to be a programmer anymore", QuestionType.MULTIPLE_CHOICE, 1,new ArrayList<>(Arrays.asList("yes","no"))));
		test2.addQuestion(new TestQuestion("5-3 equals?", QuestionType.NUMBER, 2));
		test2.addQuestion(new TestQuestion("3+2 equals?", QuestionType.MULTIPLE_CHOICE, 0,new ArrayList<>(Arrays.asList("5","1","6"))));
		
		return new ArrayList<>(Arrays.asList(test1,test2));
	}
	
	public static ArrayList<User> getUsers() {
		User user1 = new User("admin", "admin", "admin", "admin");
		user1.setPermissionLevel(PermissionLevel.ADMINISTRATOR);
		User user2 = new User("john", "parrot", "student", "student");
		user2.setPermissionLevel(PermissionLevel.STUDENT);
		user2.setID(1);
		User user3 = new User("fairold", "breadstale", "wizardry", "spells");
		user3.setPermissionLevel(PermissionLevel.STUDENT);
		user3.setID(2);
		User user4 = new User("bethany", "greenapple", "teacher","teacher");
		user4.setPermissionLevel(PermissionLevel.EDUCATOR);
		user4.setID(3);
		
		return new ArrayList<>(
				Arrays.asList(user1,user2,user3,user4)
				);
	}

}
