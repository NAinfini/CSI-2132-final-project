import java.util.Scanner;

public class employeePage {
	private boolean active = true;
	private int Booking_ID;
	private String first_name;
	private String last_name;
	
	employeePage(){
		String userInput;
		Boolean customerValid = false;
		System.out.print("\nHello, you logged in as a employee\n\n"); 
		
		while(!customerValid) {
			System.out.print("What is the Booking ID of this customer?\n");
			System.out.print("Booking ID number: ");
			
			Scanner myObj = new Scanner(System.in);
			this.checkBooking(myObj);
			
			System.out.print("This customer is Mr/Mrs "+this.first_name+" "+ this.last_name+ ", Do you confirm their identity? (yes/no)\n");
			
			Boolean response = true;
			while(response) {
				userInput = myObj.nextLine().trim();
				switch(userInput) {
				case "yes":
					response = false;
					customerValid = true;
					break;
				case "no":
					System.out.print("\nPlease make sure that the customer has a booking\n");
					response = false;
					customerValid = false;
					break;
				default:
					System.out.print("Invalid response, please try again!\n");
				}
			}
		}
		
		
		
		System.out.print("\nloop ended\n");
		
		
	}
	
	/*
	 * Update the first name and last name of the customer 
	 */
	private void updateFullName(int booking_id) {
		String[] result = accessDataBase.getInstance().getFullName(booking_id);
		this.first_name = result[0];
		this.last_name = result[1];
	 }
	
	/*
	 * Check if the booking is valid  
	 */
	private void checkBooking(Scanner myObj) {
		String userInput;
		while(true) {
			userInput = myObj.nextLine().trim();
			
			// Check if the input is a valid Integer
			if(isInteger(userInput)) {
				this.Booking_ID = Integer.parseInt(userInput);
				this.updateFullName(this.Booking_ID);
				
				// If the booking ID doesn't exist, ask the customer to retype the booking ID
				// else, the booking is valid
				if (this.first_name == null || this.last_name == null) {
					System.out.print("\nPlease enter a valid Booking ID!\n");
					System.out.print("Booking ID number: ");
				} else {
					break;
				}
				
			} else {
				System.out.print("\nPlease enter a valid number!\n");
				System.out.print("Booking ID number: ");
			}
			
		}
	}
	 

	/*
	 * This Helper method is used to determine whether the user
	 * entered a valid integer or not
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
