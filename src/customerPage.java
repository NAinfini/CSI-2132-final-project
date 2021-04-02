import java.util.Scanner;

public class customerPage {
	private boolean active = true;
	
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
			System.out.print("is a command\n"); 
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
		if(userInput.contains(" ")){
			temp = temp.substring(0, temp.indexOf(" "));
	    }
	}
}
