import java.util.Scanner;

public class mainProgram {
	
	 public static void main(String args[]) {
		 mainProgram program = new mainProgram();
		 Scanner myObj = new Scanner(System.in);
		 System.out.print("Hello, welcome to the hotel thingy"); 
		 System.out.print("please enter your username, or regiter to register a new account"); 
		 String userInput = myObj.nextLine();
		 while(!userInput.equals("register") && !program.validateUserName(userInput)) {
			 System.out.print("invalid username, please try again"); 
			 userInput = myObj.nextLine();
		 }
		 if(userInput.equals("register")) {
			 program.registerUser();
		 }else {
			 if(program.getUserType(userInput).equals("customer")) {
				 customerPage customer= new customerPage();
			 }else if(program.getUserType(userInput).equals("employee")) {
				 employeePage employee = new employeePage();
			 }else {
				 System.out.print("Your type doesnt exist in database, something went wrong"); 
			 }
		 }
	 }
	 
	 private boolean validateUserName(String userName) {
		 return accessDataBase.getInstance().validateUserName(userName);
	 }
	 private String getUserType(String userInput) {
		 return accessDataBase.getInstance().getUserType(userInput);
	 }
	 private boolean registerUser() {
		 return true;
	 }
	 
}
