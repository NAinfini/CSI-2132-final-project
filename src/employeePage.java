import java.util.Scanner;

public class employeePage {
	private boolean active = true;
	private int Booking_ID;
	private String first_name;
	private String last_name;
	
	employeePage(){
		String userInput;
		System.out.print("\nHello, you logged in as a employee\n\n"); 
		System.out.print("What is the Booking ID of this customer?\n");
		System.out.print("Booking ID number: ");
		Scanner myObj = new Scanner(System.in);
		
		while(true) {
			userInput = myObj.nextLine().trim();
			if(isInteger(userInput)) {
				this.Booking_ID = Integer.parseInt(userInput);
				break;
			} else {
				System.out.print("\nPlease enter a valid number!\n");
				System.out.print("Booking ID number: ");
			}
			
		}
		
		this.updateFullName(this.Booking_ID);
		System.out.print("This customer is Mr/Mrs "+this.first_name+" "+ this.last_name+ ", Do you confirm their identity? (yes/no)\n");
		
		
	}
	
	private void updateFullName(int booking_id) {
		String[] result = accessDataBase.getInstance().getFullName(booking_id);
		this.first_name = result[0];
		this.last_name = result[1];
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
