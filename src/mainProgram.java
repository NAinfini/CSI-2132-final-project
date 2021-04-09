import java.util.Scanner;

public class mainProgram {
	private boolean loginDone = false;
	public static String userName;
	 public static void main(String args[]) {
		 mainProgram program = new mainProgram();
		 program.loginProcess();
		 

	 }
	 //log in process
	 private void loginProcess() {
		 Scanner myObj = new Scanner(System.in);
		 System.out.print("Hello, welcome to the hotel thingy.\n"); 
		 while(!loginDone) {
			 System.out.print("please enter your username, or type \"register\" to register a new account.\n"); 
			 String userInput = myObj.nextLine().toLowerCase().trim();
			 while(!userInput.equals("register") && !validateUserName(userInput)) {
				 System.out.print("invalid username, please try again\n"); 
				 userInput = myObj.nextLine();
			 }
			 if(userInput.equals("register")) {
				 registerUser();
			 }else {
				 userName = userInput;
				 System.out.print("Enter password:\n"); 
				 userInput = myObj.nextLine();
				 while(!validatePassword(userName,userInput)) {
					 System.out.print("Wrong password, try again:\n"); 
					 System.out.print("Your username:"+userName+"\n"); 
					 userInput = myObj.nextLine();
				 }
				 System.out.print("login successfull.\n"); 
				 if(getUserType(userName).equals("customer")) {
					 loginDone = true;
					 new customerPage();
					 
				 }else if(getUserType(userName).equals("employee")) {
					 loginDone = true;
					 new employeePage();
				 }else {
					 System.out.print("Your type doesnt exist in database, something went wrong\n"); 
				 }
			 }
		 }
		 myObj.close();
	 }
	
	 private boolean validateUserName(String userName) {
		 return accessDataBase.getInstance().validateUserName(userName);
	 }
	 private String getUserType(String userInput) {
		 return accessDataBase.getInstance().getUserType(userInput);
	 }
	 
	 //take user inputs and register as a new user
	 private boolean registerUser() {
		 boolean inputValid = false;
		 boolean inputNotNull = false;
		 boolean userNameValid = false;
		 boolean hotelIDValid = false;
		 String hotel_ID = null;
		 String firstName= null;
		 String lastName= null; 
		 String sin= null;
		 String username= null;
		 String password= null;
		 String aptNumber= null;
		 String streetNum= null;
		 String streetName= null;
		 String city= null;
		 String province= null;
		 String country= null;
		 String postal= null;
		 String userType = null;
		 Scanner myObj = new Scanner(System.in);
		 while(!inputValid || !inputNotNull || !userNameValid || !hotelIDValid) {
			 System.out.print("enter hotel id here: \n"); 
			 hotel_ID = myObj.nextLine();
			 System.out.print("enter first name here: \n"); 
			 firstName = myObj.nextLine();
			 System.out.print("enter last name here: \n"); 
			 lastName = myObj.nextLine();
			 System.out.print("enter sin here: \n"); 
			 sin = myObj.nextLine();
			 System.out.print("enter username here: \n"); 
			 username = myObj.nextLine();
			 System.out.print("enter password here: \n"); 
			 password = myObj.nextLine();
			 System.out.print("enter apt number here: \n"); 
			 aptNumber = myObj.nextLine();
			 System.out.print("enter street number here: \n"); 
			 streetNum = myObj.nextLine();
			 System.out.print("enter street name here: \n"); 
			 streetName = myObj.nextLine();
			 System.out.print("enter city here: \n"); 
			 city = myObj.nextLine();
			 System.out.print("enter province here: \n"); 
			 province = myObj.nextLine();
			 System.out.print("enter country here: \n"); 
			 country = myObj.nextLine();
			 System.out.print("enter postal here: \n"); 
			 postal = myObj.nextLine();
			 do {
				 System.out.print("enter type of accouunt here: (customer, employee)\n"); 
				 userType = myObj.nextLine();
			 }while(!userType.equals("customer") && !userType.equals("employee"));
			 if(isInteger(sin) && isInteger(hotel_ID) && isInteger(aptNumber) && isInteger(streetNum)) {
				 inputValid=true;
			 }else {
				 System.out.print("sin,hotel_ID,apt number,street number has to be integer. \n"); 
			 }
			 if( !hotel_ID.isBlank() && !firstName.isBlank() && !lastName.isBlank() && !country.isBlank()
					 && !city.isBlank() && !streetName.isBlank() && !username.isBlank() && !password.isBlank()) {
				 inputNotNull=true;
			 }else {
				 System.out.print("hotel ID, first last name, country, city, street name, username and password can not be null. \n"); 
			 }
			 if(!accessDataBase.getInstance().validateUserName(username)) {
				 userNameValid = true;
			 }else {
				 System.out.print("username already in use\n");
			 }
			 if(accessDataBase.getInstance().validateHotelID(hotel_ID)) {
				 hotelIDValid=true;
			 }else {
				 System.out.print("invalid hotel ID\n"); 
			 }
		 }
		 
		 try {
			 String userID =accessDataBase.getInstance().registerUser(hotel_ID,firstName,lastName,
					 accessDataBase.getInstance().registerAddress(aptNumber,streetNum,streetName,city,province,country,postal)
					 ,sin,username,password);
			 if(userType.equals("customer")) {
				 addCustomer(userID);
			 }else if (userType.equals("employee")) {
				 addEmployee(userID);
			 }else {
				 System.out.print("How did you bypass the user type check? it is impossible ot get here\n"); 
			 }
			 myObj.close();
			 return true;
		 }catch(Exception e) {
			 System.out.print("Something went wrong creating account :"+ e.toString()+"\n"); 
			 myObj.close();
			 return false;
		 }
		 
		 
	 }
	
	 //check if a string is an int
	 private boolean isInteger(String str) {
		    if (str == null) {
		        return false;
		    }
		    int length = str.length();
		    if (length == 0) {
		        return false;
		    }
		    int i = 0;
		    if (str.charAt(0) == '-') {
		        if (length == 1) {
		            return false;
		        }
		        i = 1;
		    }
		    for (; i < length; i++) {
		        char c = str.charAt(i);
		        if (c < '0' || c > '9') {
		            return false;
		        }
		    }
		    return true;
		}	 
	 
	 private boolean validatePassword(String username, String password) {
		 return accessDataBase.getInstance().validatePassword(username,password);
	 }

	 private void addCustomer(String userID) {
		 if(accessDataBase.getInstance().addCustomer(userID)) {
			 System.out.print("Successfulyl added as a customer\n"); 
		 }
	 }
	 private void addEmployee(String userID) {
		 if(accessDataBase.getInstance().addEmployee(userID)) {
			 System.out.print("Successfully added as an employee\n"); 
		 }
		 
	 } 
}

