import java.util.Scanner;

public class mainProgram {
	private boolean loginDone = false;
	 public static void main(String args[]) {
		 mainProgram program = new mainProgram();
		 program.loginProcess();
		 

	 }
	 private void loginProcess() {
		 Scanner myObj = new Scanner(System.in);
		 System.out.print("Hello, welcome to the hotel thingy.\n"); 
		 while(!loginDone) {
			 System.out.print("please enter your username, or type \"regiter\" to register a new account.\n"); 
			 String userInput = myObj.nextLine();
			 while(!userInput.equals("register") && !validateUserName(userInput)) {
				 System.out.print("invalid username, please try again\n"); 
				 userInput = myObj.nextLine();
			 }
			 if(userInput.equals("register")) {
				 registerUser();
			 }else {
				 if(getUserType(userInput).equals("customer")) {
					 loginDone = true;
					 customerPage customer= new customerPage();
				 }else if(getUserType(userInput).equals("employee")) {
					 loginDone = true;
					 employeePage employee = new employeePage();
				 }else {
					 System.out.print("Your type doesnt exist in database, something went wrong"); 
				 }
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
		 Scanner myObj = new Scanner(System.in);
		 System.out.print("enter username here\n"); 
		 String userInput = myObj.nextLine();
		 System.out.print(userInput + " is now the username\n"); 
		 return true;
	 }
	 
}
