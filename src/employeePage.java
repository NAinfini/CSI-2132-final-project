import java.time.LocalDate;
import java.util.Scanner;

public class employeePage {
	private boolean bookingVerification = false;
	private boolean hasBooking = true;
	private int Booking_ID;
	private String first_name;
	private String last_name;
	private String userInput;
	private Scanner myObj = new Scanner(System.in);
	private Boolean customerValid = false;
	private String userID;
	
	employeePage(){
		
		System.out.print("\nHello, you logged in as a employee\n\n"); 
		
		while(!bookingVerification) {
			System.out.println("Does this customer have a booking? (yes/no)");
			userInput = myObj.nextLine().toLowerCase().trim();
			switch(userInput) {
			case "yes":
				bookingVerification = true;
				break;
			case "no":
				bookingVerification = true;
				hasBooking = false;
				break;
			default:
				System.out.println("Invalid response, please try again!");
			}
		}
		
		if(hasBooking) {
			this.customerHasBooking();
		} else {
			this.customerHasNObooking();
		}
	}
	

	/**
	 * Method to deal with customers that has a booking
	 */
	private void customerHasBooking() {
		
		while(!customerValid) {
			System.out.print("What is the Booking ID of this customer?\n");
			System.out.print("Booking ID number: ");
			
			
			this.checkBooking(myObj);
			
			System.out.print("This customer is Mr/Mrs "+this.first_name+" "+ this.last_name+ ", Do you confirm their identity? (yes/no)\n");
			
			Boolean response = true;
			while(response) {
				userInput = myObj.nextLine().trim().toLowerCase();
				switch(userInput) {
				case "yes":
					response = false;
					customerValid = true;
					break;
				case "no":
					System.out.print("\nPlease make sure that the customer has a booking\n");
					response = false;
					customerValid = false;
					System.exit(0);
					break;
				default:
					System.out.print("Invalid response, please try again!\n");
				}
			}
		}
		
		try {
			
			//transform it to a renting
			accessDataBase.getInstance().transformToRenting(Booking_ID);
			System.out.println("Room successfully rented!");
			System.exit(0);
		} catch (Exception e) {
			System.out.print("There was a problem when transforming to a renting, please try again!\n");
			System.exit(0);
		}
	}
	
	/**
	 * Handling customers with no booking
	 */
	private void customerHasNObooking() {
		System.out.println("This customer has no booking");
		System.out.println("In order to rent a room for them, please insert their info: ");
		this.registerUser();
		this.RentRoom();
		System.out.println("Room is successfully rented");
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
	
	/*
	 * Register the customer 
	 */
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
				 System.out.print("The type of user is 'Customer' ."); 
				 userType = "customer";
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
				 this.userID =accessDataBase.getInstance().registerUser(hotel_ID,firstName,lastName,
						 accessDataBase.getInstance().registerAddress(aptNumber,streetNum,streetName,city,province,country,postal)
						 ,sin,username,password);
				 if(userType.equals("customer")) {
					 if(accessDataBase.getInstance().addCustomer(userID)) {
						 System.out.print("Successfulyl added customer's account\n"); 
					 }
				 }else {
					 System.out.print("How did you bypass the user type check? it is impossible ot get here\n"); 
				 }
				 myObj.close();
				 return true;
			 }catch(Exception e) {
				 System.out.print("Something went wrong creating account\n"); 
				 myObj.close();
				 return false;
			 }
		 }
		 
		 /*
		  * Rent a room
		  */
		 private void RentRoom() {
				boolean proceed = false;
				boolean checkOutValid = false;
				boolean datesMatch = false;
				int hotel_ID = 0;
				int room_id = 0;
				String checkInDate = "";
				String checkOutDate = "";
				String credit;
				Scanner myObj = new Scanner(System.in);
				
				// check the current date
				LocalDate local_date = LocalDate.now();        	
				checkInDate = local_date.toString().trim();
				System.out.println("Customer check-in is on the " + checkInDate);
					
					
				proceed = false;
				while (!proceed) {
					System.out.print("Successfully checked in, now choose the check-out date: (yyyy-mm-dd)\n");
					while (!datesMatch) {
						checkOutDate = myObj.nextLine().trim();
						checkOutValid = isValidDate(checkOutDate);
						if (checkOutValid) {
							datesMatch = isAfter(checkInDate, checkOutDate);
						}
					}
					System.out.print("Customer will check out on the " + checkOutDate + ", do you confirm this? (yes/no)\n");
					proceed = myObj.nextLine().equals("yes");
				}
				proceed = false;
				while(!proceed) {
					System.out.println("Choose your Hotel ID: ");
					String str = myObj.nextLine().trim();
					if(isInteger(str)) {
						hotel_ID = Integer.parseInt(str);
						proceed = true;
					} else {
						System.out.println("Please insert valid hotel ID");
					}
				}
				
				proceed = false;
				while(!proceed) {
					System.out.println("Choose a room for the customer: ");
					String str = myObj.nextLine().trim();
					if(isInteger(str)) {
						room_id = Integer.parseInt(str);
						proceed = true;
					} else {
						System.out.println("Please insert valid number");
					}
				}
				
				System.out.print("The payment method by default is in person\n");
				
				proceed = false;
				while (!proceed) {
					System.out.print("Please insert the credit card number: \n");
					credit = myObj.nextLine().trim();
					System.out
							.print("You have entered this credit card number: " + credit + ", do you confirm this? (yes/no)\n");
					proceed = (myObj.nextLine().toLowerCase().trim()).equals("yes");
				}

				// Check if the submission was successful
				try {
					accessDataBase.getInstance().createRenting(Integer.parseInt(userID), hotel_ID, room_id, checkOutDate, checkInDate);
					System.out.print("Rent was successfully submited!");
					System.exit(0);
				} catch (Exception e) {
					System.out.print("Sorry, there was an error on our end, please try again later."+e.toString());
				}
				myObj.close();
			}
		 
		 /**
			 * Verifies if the date of check-out is after the day of check-out
			 * 
			 * @param check_in  date
			 * @param check_out date
			 * @return true if valid
			 */
			public static boolean isAfter(String check_in, String check_out) {

				// Integer values of check_in
				int yearIn = Integer.parseInt(check_in.substring(0, 4));
				int monthIn = Integer.parseInt(check_in.substring(5, 7));
				int dayIn = Integer.parseInt(check_in.substring(8, 10));

				// Integer values of check_out
				int yearOut = Integer.parseInt(check_out.substring(0, 4));
				int monthOut = Integer.parseInt(check_out.substring(5, 7));
				int dayOut = Integer.parseInt(check_out.substring(8, 10));

				// Check if year is valid
				if (yearOut < yearIn) {
					System.out.println("Year can not be less than the check-in date. Please try again.");
					return false;
				} else if (yearOut == yearIn) {
					// Now check for the month
					if (monthOut < monthIn) {
						System.out.println("Month can not be less than the check-in date. Please try again.");
						return false;
					} else if (monthOut == monthIn) {
						// Now check for the day
						if (dayOut < dayIn) {
							System.out.println("Day can not be less than the check-in date. Please try again.");
							return false;
						} else if (dayOut == dayIn) {
							System.out.println("You have to check for at least one day. Please try again.");
							return false;
						}
					}
				}

				// All tests passed successfully
				return true;

			}

			/**
			 * Validate if the date inserted by the user is valid or not
			 * 
			 * @param value to validate
			 * @return true if valid
			 */
			public static boolean isValidDate(String value) {

				// Check the length of date is acceptable
				if (value.length() != 10) {
					System.out.println("Please make sure you used the format yyyy-mm-dd.");
					System.out.println("Please add '0' if the month or day is less then 10, example 04 for April.");
					return false;
				}

				// Check is the format has '-' in it
				if (!value.substring(4, 5).equals("-") || !value.substring(7, 8).equals("-")) {
					System.out.println("Please make sure you used the format yyyy-mm-dd.");
					System.out.println("Please make sure to use the 'Hyphen : - ' when inserting the date.");
					return false;
				}

				// Values of the date
				String year = value.substring(0, 4);
				String month = value.substring(5, 7);
				String day = value.substring(8, 10);

				// Check if all vars are integers
				if (!isInteger(year)) {
					System.out.println("Please make sure that Year is a valid number.");
					return false;
				}
				if (!isInteger(month)) {
					System.out.println("Please make sure that Month is a valid number.");
					return false;
				}
				if (!isInteger(day)) {
					System.out.println("Please make sure that day is a valid number.");
					return false;
				}

				// Integer values of dates
				int yearINT = Integer.parseInt(year);
				int monthINT = Integer.parseInt(month);
				int dayINT = Integer.parseInt(day);

				// Check if all vars are greater than 0
				if (yearINT < 0) {
					System.out.println("Please make sure that Year is a valid number.");
					return false;
				}
				if (monthINT < 0) {
					System.out.println("Please make sure that Month is a valid number.");
					return false;
				}
				if (dayINT < 0) {
					System.out.println("Please make sure that day is a valid number.");
					return false;
				}

				// check if dates are in range
				if (yearINT < 2021) {
					System.out.println("Please make sure that Year is in range.");
					return false;
				}
				if (monthINT < 1 || monthINT > 12) {
					System.out.println("Please make sure that Month is between 1 and 12.");
					return false;
				}
				if (dayINT < 1 || dayINT > 31) {
					System.out.println("Please make sure that Day is between 1 and 31.");
					return false;
				}

				return true;
			}
	
}
