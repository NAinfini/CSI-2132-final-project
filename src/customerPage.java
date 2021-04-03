import java.util.Scanner;

public class customerPage {
	private boolean active = true;
	private String currentLevel = "parentHotel";
	private int brandID = 0;
	private int hotelID = 0;
	customerPage(){
		System.out.print("hello, you logged in as a customer\n"); 
		System.out.print("You can see all command lines by typing /help \n"); 
		Scanner myObj = new Scanner(System.in);
		String userInput;
		while(active) {
			userInput = myObj.nextLine();
			if(userInput.length()>=1) {
				checkCommand(userInput);
			}
			
		}
		
	}
	
	private void checkCommand(String userInput) {
		if(isCommand(userInput)) {
			checkCommandType(userInput.substring(1));
		}else {
			System.out.print("not a command\n"); 
		}
		
	}
	
	private boolean isCommand(String userInput) {
		char ch1 = userInput.charAt(0);
		return ch1=='/';
	}
	
	private void checkCommandType(String userInput) {
		String temp = userInput;
		String id = "";
		int integerIForID = 0;
		if(userInput.contains(" ")){
			temp = temp.substring(0, temp.indexOf(" "));
			 id = userInput.substring(userInput.indexOf(' ') + 1).trim();
	    }
		switch(temp) {
		  	case "help":
		  		displayHelp();
		  		break;
		  	case "goto":
		  		try {
		  			integerIForID=Integer.parseInt(id);  
		  		}catch(NumberFormatException e) {
		  			System.out.print("Please enter a valid id\n"); 
		  		}
		  		if(brandID == 0) {
		  			brandID = integerIForID;
		  			System.out.print("You are at brand ID = " + integerIForID+"\n"); 
		  			displayHotels();
		  		}else if(hotelID == 0) {
		  			hotelID = integerIForID;
		  			System.out.print("You are at hotel ID = " + integerIForID+"\n"); 
		  		}else {
		  			System.out.print("can not go deeper\n"); 
		  		}
		  		break;
		  	case "brands":
		  		displayBrands();
		  		brandID = 0;
		  		hotelID = 0;
		  		break;
		  	case "back":
			    if(brandID == 0) {
			    	System.out.print("can not go back more\n"); 
			    }else if (hotelID == 0) {
			    	displayBrands();
			    	System.out.print("You are at brand ID = " + brandID+"\n"); 
			    	brandID = 0;
			    }else {
			    	displayHotels();
			    	hotelID = 0;
			    }
		  		break;
		  	case "book":
		  		if(hotelID == 0) {
		  			System.out.print("Please goto a hotel first\n"); 
		  		}else {
		  			bookRoom();
		  		}
		  		break;
		  	case "exit":
		  		active = false;
		  		System.exit(0);
		  		break;
		  	default:
		    
		}
	}
	
	private void displayHelp() {
		System.out.print("List of commands: \n");
		System.out.print("help: display all commands\n");
		System.out.print("brands: display all hotel brands \n");
		System.out.print("goto: enter next level of directories with given id\n");
		System.out.print("back: go back a level in directory\n");
		System.out.print("book: book a room in a hotel \n");
		System.out.print("exit: exit program \n");
	}
	
	private void displayBrands() {
		System.out.print(accessDataBase.getInstance().getBrands());
	}

	private void displayHotels(){
		System.out.print(accessDataBase.getInstance().getHotels(brandID));
	}
	
	private void bookRoom() {
		boolean proceed = false;
		String checkInDate = "";
		String checkOutDate;
		String paymentMethod = "";
		String credit;
		Scanner myObj = new Scanner(System.in);
		//not protected against invalid dates
		while(!proceed) {
			System.out.print("Choose your checking date: (yyyy-mm-dd)\n");
			checkInDate = myObj.nextLine();
			System.out.print("You have checked in on the "+checkInDate+", do you confirm this? (yes/no)\n");
			proceed =  myObj.nextLine().equals("yes");
		}
		proceed = false;
		while(!proceed) {
			System.out.print("You have successfully checked in, now choose you check-out date: (yyyy-mm-dd)\n");
			checkOutDate = myObj.nextLine();
			System.out.print("You have checked out on the "+checkOutDate+", do you confirm this? (yes/no)\n");
			proceed =  myObj.nextLine().equals("yes");
		}
		proceed = false;
		do {
			System.out.print("Would you like to pay online OR in person ? (online/person)\n");
			paymentMethod = myObj.nextLine();
		}while(!paymentMethod.equals("online")&&!paymentMethod.equals("person"));
		
		while(!proceed) {
			System.out.print("Please insert you credit card number\n");
			credit = myObj.nextLine();
			System.out.print("You have entered:"+credit+", do you confirm this? (yes/no)\n");
			proceed =  myObj.nextLine().equals("yes");
		}
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< Updated upstream
<<<<<<< Updated upstream
		accessDataBase.getInstance().bookRoom(hotelID,mainProgram.userName,checkInDate,paymentMethod);
=======
=======
>>>>>>> Stashed changes
		if(accessDataBase.getInstance().bookRoom(hotelID,mainProgram.userName,checkInDate,checkOutDate,paymentMethod)) {
			System.out.print("booking successful\n");
		}else {
			System.out.print("booking failed\n");
		}
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
		accessDataBase.getInstance().bookRoom(hotelID,mainProgram.userName,checkInDate,checkOutDate,paymentMethod);
>>>>>>> a6cd17fae0e20fd857eb34fb40e014d0592759a5
=======
		accessDataBase.getInstance().bookRoom(hotelID,mainProgram.userName,checkInDate,paymentMethod);
>>>>>>> parent of d8b6032 (date format fixed)
=======
		accessDataBase.getInstance().bookRoom(hotelID,mainProgram.userName,checkInDate,paymentMethod);
>>>>>>> parent of 2806f77 (Merge pull request #7 from NAinfini/Daniel)
	}
}
