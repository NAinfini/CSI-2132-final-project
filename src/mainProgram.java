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
		 String hotel_ID;
		 String firstName;
		 String lastName; 
		 String sin;
		 String username;
		 String password;
		 String aptNumber;
		 String streetNum;
		 String streetName;
		 String city;
		 String province;
		 String country;
		 String postal;
		 Scanner myObj = new Scanner(System.in);
		 System.out.print("enter hotel id here\n"); 
		 hotel_ID = myObj.nextLine();
		 System.out.print("enter first name here\n"); 
		 firstName = myObj.nextLine();
		 System.out.print("enter last name here\n"); 
		 lastName = myObj.nextLine();
		 System.out.print("enter sin here\n"); 
		 sin = myObj.nextLine();
		 System.out.print("enter username here\n"); 
		 username = myObj.nextLine();
		 System.out.print("enter password here\n"); 
		 password = myObj.nextLine();
		 System.out.print("enter apt number here\n"); 
		 aptNumber = myObj.nextLine();
		 System.out.print("enter street number here\n"); 
		 streetNum = myObj.nextLine();
		 System.out.print("enter street name here\n"); 
		 streetName = myObj.nextLine();
		 System.out.print("enter city here\n"); 
		 city = myObj.nextLine();
		 System.out.print("enter province here\n"); 
		 province = myObj.nextLine();
		 System.out.print("enter country here\n"); 
		 country = myObj.nextLine();
		 System.out.print("enter postal here\n"); 
		 postal = myObj.nextLine();
		 try {
			 accessDataBase.getInstance().registerUser(hotel_ID,firstName,lastName,
					 accessDataBase.getInstance().registerAddress(aptNumber,streetNum,streetName,city,province,country,postal)
					 ,sin,username,password);
			 return true;
		 }catch(Exception e) {
			 System.out.print("Something went wrong creating account\n"); 
			 return false;
		 }
		 
		 
	 }
	 
}
