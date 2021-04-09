import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class accessDataBase {
	private static accessDataBase dataBase = null;
	private Statement stmt;
	ResultSet rs;

	private accessDataBase() {

		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.out.print("JDBC driver not found ");
		}

		Connection conn;
		try {
			do {
				conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07",
						"msui005", "Z4321zxeZ4321zxe");
				stmt = conn.createStatement();
			} while (conn.isClosed());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Creates only 1 instance of access to database per process
	public static accessDataBase getInstance() {
		if (dataBase == null)
			dataBase = new accessDataBase();

		return dataBase;
	}

	// validates of the username is in the database
	boolean validateUserName(String userName) {
		try {
			rs = stmt.executeQuery("SELECT username FROM hotel.person");
			while (rs.next()) {
				if (rs.getString(1).equals(userName)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// validates if the user is a customer or an employee
	String getUserType(String userInput) {
		try {
			String userID = getUserID(userInput);
			rs = stmt.executeQuery("select * from hotel.customer where person_id = " + userID + " ;");
			String result = "";
			if (rs.next()) {
				return "customer";
			} else {
				rs = stmt.executeQuery("select * from hotel.employee where person_id = " + userID + " ;");
				if (rs.next()) {
					return "employee";
				}
				System.out.print("Something went wrong,your type doesnt exist.\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// add address to the database
	String registerAddress(String aptNumber, String streetNum, String streetName, String city, String province,
			String country, String postal) {
		String addressID = getNextIndex("hotel.address", "address_id");
		try {
			stmt.executeUpdate(
					"INSERT INTO hotel.address(address_id, apt_number,street_number,street_name,city,province,country,postal_code) \r\n"
							+ "VALUES (" + addressID + "," + aptNumber + "," + streetNum + ",\'" + streetName + "\',\'"
							+ city + "\',\'" + province + "\',\'" + country + "\',\'" + postal + "\');");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addressID;
	}

	String getUserID(String userName) {
		try {
			rs = stmt.executeQuery("SELECT person_id FROM hotel.person where username = \'" + userName + "\';");
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// add current user to database, uses registerAddress
	String registerUser(String hotel_ID, String firstName, String lastName, String address, String sin, String username,
			String password) {
		String personID = getNextIndex("hotel.person", "person_id");
		try {
			stmt.executeUpdate(
					"INSERT INTO hotel.person(person_id,hotel_id, first_name, last_name, address_id, sin, username, password) \r\n"
							+ "VALUES (" + personID + "," + hotel_ID + ",\'" + firstName + "\',\'" + lastName + "\',"
							+ address + "," + sin + ",\'" + username + "\',\'" + password + "\');");
			return personID;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return personID;
	}

	// add person as cutomer/employee
	boolean addCustomer(String personID) {
		try {
			stmt.executeUpdate("INSERT INTO hotel.customer(person_id) \r\n" + "VALUES (" + personID + ");");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	boolean addEmployee(String personID) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDateTime now = LocalDateTime.now();
			stmt.executeUpdate("INSERT INTO hotel.employee(person_id,registration_date) \r\n" + "VALUES (" + personID
					+ ",\'" + dtf.format(now) + "\');");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// get next empty ID for dynamic allocation
	String getNextIndex(String table, String idName) {
		try {
			rs = stmt.executeQuery("	SELECT  " + idName + "\r\n" + "	FROM    (\r\n" + "	        SELECT  1 AS "
					+ idName + "\r\n" + "	        ) q1\r\n" + "	WHERE   NOT EXISTS\r\n" + "	        (\r\n"
					+ "	        SELECT  1\r\n" + "	        FROM    " + table + "\r\n" + "	        WHERE   " + idName
					+ " = 1\r\n" + "	        )\r\n" + "	UNION ALL\r\n" + "	SELECT  *\r\n" + "	FROM    (\r\n"
					+ "	        SELECT  " + idName + " + 1\r\n" + "	        FROM    " + table + " t\r\n"
					+ "	        WHERE   NOT EXISTS\r\n" + "	                (\r\n" + "	                SELECT  1\r\n"
					+ "	                FROM    " + table + " ti\r\n" + "	                WHERE   ti." + idName
					+ " = t." + idName + " + 1\r\n" + "	                )\r\n" + "	        ORDER BY\r\n"
					+ "	        	" + idName + "\r\n" + "	        LIMIT 1\r\n" + "	        ) q2\r\n"
					+ "	ORDER BY\r\n" + "		" + idName + "\r\n" + "	LIMIT 1");
			if (rs.next()) {
				return rs.getString(1).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Empty address id");
		return "";
	}

	// check if the hotel id exists before inserting person to ensure no error
	boolean validateHotelID(String hotelID) {
		try {
			rs = stmt.executeQuery("SELECT hotel_id FROM hotel.hotel");
			while (rs.next()) {
				if (rs.getString(1).equals(hotelID)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// check if password matches username
	boolean validatePassword(String username, String password) {
		try {
			rs = stmt.executeQuery("SELECT password FROM hotel.person where username = \'" + username + "\';");
			while (rs.next()) {
				if (rs.getString(1).equals(password)) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("Something went wrong with the password, please try again\n");
		return false;
	}

	// return all brands as a string
	String getBrands() {
		try {
			String result = "";
			rs = stmt.executeQuery("SELECT * FROM hotel.parenthotel");
			return getStringFromRS(rs.getMetaData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// return all hotel in brand as a string
	String getHotels(int brandID) {
		try {
			String result = "";
			rs = stmt.executeQuery("SELECT * FROM hotel.hotel where brand_id = \'" + brandID + "\'");
			return getStringFromRS(rs.getMetaData());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	// book a room and add it in booking table
	boolean bookRoom(int roomID,int hotelID, String userName, String date, String checkOutDate, String paymentMethod) {
		String bookingID = getBookingID(hotelID, "booking_id");
		String roomNum = getRoomID(hotelID, "room_number");
		String userID = getUserID(userName);
		try {
			stmt.executeUpdate(
					"INSERT INTO hotel.booking(booking_id,hotel_id, room_number,person_id, check_in,check_out) \r\n"
							+ "VALUES (" + bookingID + "," + hotelID + "," + roomNum + "," + userID + ",\'" + date
							+ "\',\'" + checkOutDate + "\');");
			stmt.executeUpdate("UPDATE hotel.customer set payment_info = \'" + paymentMethod + "\' where person_id = \'"
					+ userID + "\'; \r\n");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// return next empty roomID
	String getRoomID(int hotelID, String idName) {
		try {
			rs = stmt.executeQuery("	SELECT  " + idName + "\r\n" + "	FROM    (\r\n" + "	        SELECT  1 AS "
					+ idName + "\r\n" + "	        ) q1\r\n" + "	WHERE   NOT EXISTS\r\n" + "	        (\r\n"
					+ "	        SELECT  1\r\n" + "	        FROM    (SELECT * FROM hotel.room where hotel_id ="
					+ hotelID + ")q3\r\n" + "	        WHERE   " + idName + " = 1\r\n" + "	        )\r\n"
					+ "	UNION ALL\r\n" + "	SELECT  *\r\n" + "	FROM    (\r\n" + "	        SELECT  " + idName
					+ " + 1\r\n" + "	        FROM    (SELECT * FROM hotel.room where hotel_id =" + hotelID
					+ ") t\r\n" + "	        WHERE   NOT EXISTS\r\n" + "	                (\r\n"
					+ "	                SELECT  1\r\n"
					+ "	                FROM    (SELECT * FROM hotel.room where hotel_id =" + hotelID + ") ti\r\n"
					+ "	                WHERE   ti." + idName + " = t." + idName + " + 1\r\n"
					+ "	                )\r\n" + "	        ORDER BY\r\n" + "	        	" + idName + "\r\n"
					+ "	        LIMIT 1\r\n" + "	        ) q2\r\n" + "	ORDER BY\r\n" + "		" + idName + "\r\n"
					+ "	LIMIT 1");
			if (rs.next()) {
				return rs.getString(1).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Empty address id");
		return "";
	}

	// returrn next empty bookingID
	String getBookingID(int hotelID, String idName) {
		try {
			rs = stmt.executeQuery("	SELECT  " + idName + "\r\n" + "	FROM    (\r\n" + "	        SELECT  1 AS "
					+ idName + "\r\n" + "	        ) q1\r\n" + "	WHERE   NOT EXISTS\r\n" + "	        (\r\n"
					+ "	        SELECT  1\r\n" + "	        FROM    (SELECT * FROM hotel.booking where hotel_id ="
					+ hotelID + ")q3\r\n" + "	        WHERE   " + idName + " = 1\r\n" + "	        )\r\n"
					+ "	UNION ALL\r\n" + "	SELECT  *\r\n" + "	FROM    (\r\n" + "	        SELECT  " + idName
					+ " + 1\r\n" + "	        FROM    (SELECT * FROM hotel.booking where hotel_id =" + hotelID
					+ ") t\r\n" + "	        WHERE   NOT EXISTS\r\n" + "	                (\r\n"
					+ "	                SELECT  1\r\n"
					+ "	                FROM    (SELECT * FROM hotel.booking where hotel_id =" + hotelID + ") ti\r\n"
					+ "	                WHERE   ti." + idName + " = t." + idName + " + 1\r\n"
					+ "	                )\r\n" + "	        ORDER BY\r\n" + "	        	" + idName + "\r\n"
					+ "	        LIMIT 1\r\n" + "	        ) q2\r\n" + "	ORDER BY\r\n" + "		" + idName + "\r\n"
					+ "	LIMIT 1");
			if (rs.next()) {
				return rs.getString(1).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Empty address id");
		return "";
	}
	String customQuery(String query) {
		try {
			String result = "";
			rs = stmt.executeQuery(query);
			return getStringFromRS(rs.getMetaData());
		} catch (Exception e) {
			return e.toString();
		}
	}
	// Get the first name and last name
	String[] getFullName(int booking_id) {

		// variable result is an array that stores first_name and last_name
		// first_name is of index 0
		// last_name is of index 1
		String[] result = new String[2];

		try {
			// Get first name
			rs = stmt.executeQuery("SELECT p.first_name FROM hotel.person p "
					+ "WHERE p.person_id = (SELECT b.person_ID FROM hotel.booking b WHERE b.booking_id =" + booking_id
					+ ") ");
			if (rs.next()) {
				result[0] = rs.getString(1).toString();
			}

			// Get last name
			rs = stmt.executeQuery("SELECT p.last_name FROM hotel.person p "
					+ "WHERE p.person_id = (SELECT b.person_ID FROM hotel.booking b WHERE b.booking_id =" + booking_id
					+ ") ");
			if (rs.next()) {
				result[1] = rs.getString(1).toString();
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("Something went wrong with the password, please try again\n");
		return null;
	}

	/*
	 * Transform the booking into a a renting
	 */
	boolean transformToRenting(int booking) {
		try {
			stmt.executeUpdate("WITH sub AS (\r\n"
					+ "		SELECT hotel_id, room_number, person_id, check_out, check_in FROM hotel.booking \r\n"
					+ "		WHERE booking_id =" + booking + " \r\n" + "		)\r\n"
					+ "		INSERT INTO hotel.renting(payment_id, hotel_id, room_number, check_out, check_in)\r\n"
					+ "		VALUES ((SELECT person_id FROM sub),\r\n" + "			(SELECT hotel_id FROM sub),\r\n"
					+ "			(SELECT room_number FROM sub),\r\n" + "			(SELECT check_out FROM sub),\r\n"
					+ "			(SELECT check_in FROM sub));");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * Transform the booking into a a renting
	 */
	boolean createRenting(int person_id, int hotel_id, int room_number, String check_out, String check_in) {
		try {
			stmt.executeUpdate("INSERT INTO hotel.renting(payment_id, hotel_id, room_number, check_out, check_in)\r\n"
					+ "		VALUES ( " + person_id + ",\r\n" + "			" + hotel_id + ",\r\n" + "			"
					+ room_number + ",\r\n" + "			\'" + check_out + "\',\r\n" + "			\'" + check_in
					+ "\');");

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String searchByView(String input) {
		try {
			rs = stmt.executeQuery("Select * From room r \r\n"
					+ "where room_view = \'"+input+"\'\r\n"
					+ "and\r\n"
					+ "not exists \r\n"
					+ "     (Select * From booking \r\n"
					+ "      Where hotel_id = r.hotel_id\r\n"
					+ "         And room_number = r.room_number)"
					+ "and\r\n"
					+ "not exists \r\n"
					+ "			(Select * From renting \r\n"
					+ "			 Where hotel_id = r.hotel_id\r\n"
					+ "				And room_number = r.room_number);");
			return getStringFromRS(rs.getMetaData());
		} catch (Exception e) {
			return e.toString();
		}
	}

	public String searchByCap(int input) {
		try {
			rs = stmt.executeQuery("Select * From room r \r\n"
					+ "where capacity >= "+input+"\r\n"
					+ "and\r\n"
					+ "not exists \r\n"
					+ "     (Select * From booking \r\n"
					+ "      Where hotel_id = r.hotel_id\r\n"
					+ "         And room_number = r.room_number)"
					+ "and\r\n"
					+ "not exists \r\n"
					+ "			(Select * From renting \r\n"
					+ "			 Where hotel_id = r.hotel_id\r\n"
					+ "				And room_number = r.room_number);");
			return getStringFromRS(rs.getMetaData());
		} catch (Exception e) {
			return e.toString();
		}
	}

	public String searchByPrice(int input) {
		try {
			rs = stmt.executeQuery("Select * From room r \r\n"
					+ "where price <= "+input+"\r\n"
					+ "and\r\n"
					+ "not exists \r\n"
					+ "     (Select * From booking \r\n"
					+ "      Where hotel_id = r.hotel_id\r\n"
					+ "         And room_number = r.room_number)"
					+ "and\r\n"
					+ "not exists \r\n"
					+ "			(Select * From renting \r\n"
					+ "			 Where hotel_id = r.hotel_id\r\n"
					+ "				And room_number = r.room_number);");
			return getStringFromRS(rs.getMetaData());
		} catch (Exception e) {
			return e.toString();
		}
	}

	String displayRooms(int input) {
		try {
			rs = stmt.executeQuery("Select * From room r \r\n"
					+ "where hotel_id = "+input+"\r\n"
					+ "and\r\n"
					+ "not exists \r\n"
					+ "     (Select * From booking \r\n"
					+ "      Where hotel_id = r.hotel_id\r\n"
					+ "         And room_number = r.room_number)"
					+ "and\r\n"
					+ "not exists \r\n"
					+ "			(Select * From renting \r\n"
					+ "			 Where hotel_id = r.hotel_id\r\n"
					+ "				And room_number = r.room_number);");
			return getStringFromRS(rs.getMetaData());
		} catch (Exception e) {
			return e.toString();
		}
	}
	private String getStringFromRS(ResultSetMetaData rsMeta) {
		String result = "";
		try {
			int count = rsMeta.getColumnCount();
			int i, j = 1;
			result += "\n| ";
			while (j <= count) {
				String format = "%1$-" + rsMeta.getColumnDisplaySize(j) + "s";
				String formatedValue = String.format(format, rsMeta.getColumnLabel(j));
				result += formatedValue + "| ";
				j++;
			}
			result += "\n" + new String(new char[result.length()]).replace("\0", "-");
			while (rs.next()) {
				i = 1;
				result += "\n| ";
				while (i <= count) {
					String format = "%1$-" + rsMeta.getColumnDisplaySize(i) + "s";
					String formatedValue = String.format(format, new String(rs.getBytes(i),StandardCharsets.UTF_8));
					result += formatedValue + "| ";
					i++;
				}
			}
			result += "\n";
			return result;
		} catch (Exception e) {
			return e.toString();
		}
		
	}
}
