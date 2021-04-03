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
		boolean checkInValid = false;
		boolean checkOutValid = false;
		boolean datesMatch = false;
		String checkInDate = "";
		String checkOutDate ="";
		String paymentMethod = "";
		String credit;
		Scanner myObj = new Scanner(System.in);
		while(!proceed) {
			while(!checkInValid) {
				System.out.print("Choose your checking date: (yyyy-mm-dd)\n");
				checkInDate = myObj.nextLine().trim();
				checkInValid = isValidDate(checkInDate);
			}
			System.out.print("You have checked in on the "+checkInDate+", do you confirm this? (yes/no)\n");
			proceed =  myObj.nextLine().equals("yes");
		}
		proceed = false;
		while(!proceed) {
			System.out.print("You have successfully checked in, now choose you check-out date: (yyyy-mm-dd)\n");
			while(!datesMatch) {
				checkOutDate = myObj.nextLine().trim();
				checkOutValid = isValidDate(checkOutDate);
				if(checkOutValid) {
					datesMatch = isAfter(checkInDate, checkOutDate);
				}
			}
			System.out.print("You have checked out on the "+checkOutDate+", do you confirm this? (yes/no)\n");
			proceed =  myObj.nextLine().equals("yes");
		}
		proceed = false;
		do {
			System.out.print("Would you like to pay online OR in person ? (online/person)\n");
			paymentMethod = myObj.nextLine().toLowerCase().trim();
		}while(!paymentMethod.equals("online")&&!paymentMethod.equals("person"));
		
		while(!proceed) {
			System.out.print("Please insert your credit card number: \n");
			credit = myObj.nextLine().trim();
			System.out.print("You have entered this credit card number: "+credit+", do you confirm this? (yes/no)\n");
			proceed =  (myObj.nextLine().toLowerCase().trim()).equals("yes");
		}
		
		// Check if the submission was successful
		try {
			accessDataBase.getInstance().bookRoom(hotelID,mainProgram.userName,checkInDate,checkOutDate,paymentMethod);
			System.out.print("Your booking was successfully submited!");
			System.exit(0);
		} catch (Exception e) {
			System.out.print("Sorry, there was an error on our end, please try again later.");
		}
	}
	

	/**
	 * Verifies if the date of check-out is after the day of check-out 
	 * @param check_in date
	 * @param check_out date
	 * @return true if valid
	 */
	public static boolean isAfter(String check_in, String check_out) {
        
        // Integer values of check_in
        int yearIn = Integer.parseInt(check_in.substring(0,4));
        int monthIn = Integer.parseInt(check_in.substring(5,7));
        int dayIn = Integer.parseInt(check_in.substring(8,10));
        
        // Integer values of check_out
        int yearOut = Integer.parseInt(check_out.substring(0,4));
        int monthOut = Integer.parseInt(check_out.substring(5,7));
        int dayOut = Integer.parseInt(check_out.substring(8,10));
        
        
        // Check if year is valid
        if(yearOut < yearIn) {
            System.out.println("Year can not be less than the check-in date. Please try again.");
            return false;
        } else if(yearOut == yearIn) {
            // Now check for the month
            if(monthOut < monthIn) {
                System.out.println("Month can not be less than the check-in date. Please try again.");
                return false;
            } else if(monthOut == monthIn) {
                // Now check for the day
                if(dayOut < dayIn) {
                    System.out.println("Day can not be less than the check-in date. Please try again.");
                    return false;
                } else if(dayOut == dayIn) {
                    System.out.println("You have to check for at least one day. Please try again.");
                    return false;
                }
            }
        }
        
        // All tests passed successfully
        return true;
        
    }
     

	/**
	 *  Validate if the date inserted by the user is valid or not
	 * @param value to validate
	 * @return true if valid
	 */
    public static boolean isValidDate(String value) {
        
        // Check the length of date is acceptable
        if(value.length() != 10 ) {
            System.out.println("Please make sure you used the format yyyy-mm-dd.");
            System.out.println("Please add '0' if the month or day is less then 10, example 04 for April.");
            return false;
        }
        
        // Check is the format has '-' in it
        if(!value.substring(4,5).equals("-") || !value.substring(7,8).equals("-")) {
            System.out.println("Please make sure you used the format yyyy-mm-dd.");
            System.out.println("Please make sure to use the 'Hyphen : - ' when inserting the date.");
            return false;
        }
        
        // Values of the date
        String year = value.substring(0,4);
        String month = value.substring(5,7);
        String day = value.substring(8,10);
        
        // Check if all vars are integers
        if(!isInteger(year)) {
            System.out.println("Please make sure that Year is a valid number.");
            return false;
        }
        if(!isInteger(month)) {
            System.out.println("Please make sure that Month is a valid number.");
            return false;
        }
        if(!isInteger(day)) {
            System.out.println("Please make sure that day is a valid number.");
            return false;
        }
        
        // Integer values of dates
        int yearINT = Integer.parseInt(year);
        int monthINT = Integer.parseInt(month);
        int dayINT = Integer.parseInt(day);
        
        // Check if all vars are greater than 0
        if(yearINT < 0) {
            System.out.println("Please make sure that Year is a valid number.");
            return false;
        }
        if(monthINT < 0) {
            System.out.println("Please make sure that Month is a valid number.");
            return false;
        }
        if(dayINT < 0) {
            System.out.println("Please make sure that day is a valid number.");
            return false;
        }
        
        //check if dates are in range
        if(yearINT < 2021){
            System.out.println("Please make sure that Year is in range.");
            return false;
        }
        if(monthINT < 1 || monthINT > 12){
            System.out.println("Please make sure that Month is between 1 and 12.");
            return false;
        }
        if(dayINT < 1 || dayINT > 31){
            System.out.println("Please make sure that Day is between 1 and 31.");
            return false;
        }
        
        return true;
    }
    
    /**
     * Helper function used to verifies if the number is an integer
     * @param str value to compare with
     * @return true if str is an integer
     */
    public static boolean isInteger(String str) {
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
}
