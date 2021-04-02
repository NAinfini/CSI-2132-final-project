import java.util.Scanner;

public class customerPage {
	private boolean active = true;
	private String currentLevel = "parentHotel";
	private int brandID = 0;
	private int hotelID = 0;
	private int roomNum = 0;
	customerPage(){
		System.out.print("hello, you logged in as a customer\n"); 
		System.out.print("You can see all command lines by typing /help \n"); 
		Scanner myObj = new Scanner(System.in);
		String userInput;
		while(active) {
			userInput = myObj.nextLine();
			checkCommand(userInput);
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
		  			displayHotels();
		  		}else if(hotelID == 0) {
		  			hotelID = integerIForID;
		  			displayRooms();
		  		}else if(roomNum == 0) {
		  			roomNum = integerIForID;
		  			displayOneRoom();
		  		}
		  		break;
		  	case "brands":
		  		displayBrands();
		  		brandID = 0;
		  		hotelID = 0;
		  		roomNum = 0;
		  		break;
		  	case "back":
			    // code block
		  		break;
		  	case "book":
		  		if(roomNum == 0) {
		  			System.out.print("Please goto a room first\n"); 
		  		}else {
		  			
		  		}
		  		break;
		  	case "exit":
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
	
	private void displayRooms() {
		System.out.print(accessDataBase.getInstance().getRooms(brandID,hotelID));
	}
	
	private void displayOneRoom() {
		System.out.print(accessDataBase.getInstance().getOneRoom(brandID,hotelID,roomNum));
	}
}
