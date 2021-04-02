import java.sql.*;
public class accessDataBase {
	private static accessDataBase dataBase = null;
	private Statement stmt;
	private accessDataBase(){
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.print("JDBC driver not found "); 
		}
	}
	public static accessDataBase getInstance()
    {
        if (dataBase == null)
        	dataBase = new accessDataBase();
  
        return dataBase;
    }
	boolean validateUserName(String userName) {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("SELECT username FROM hotel.person");
			while (rs.next()) {
				if(rs.getString(1).equals(userName)) {
					rs.close(); 
					stmt.close();
					return true;
				}
			} 
			rs.close(); 
			stmt.close();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	 }
	String getUserType(String userInput) {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("select person_id from hotel.customer where exists(SELECT person_id FROM hotel.person where username = \'"+ userInput+"\')");
			String result = "";
			if (rs.next()) {
				result = rs.getString(1).toString();
			}
			if(result.isBlank()) {
				rs = stmt.executeQuery("select person_id from hotel.employee where exists(SELECT person_id FROM hotel.person where username = \'"+ userInput+"\')");
				if (rs.next()) {
					result = rs.getString(1).toString();
				}
				if(result.isBlank()) {
					System.out.print("Something went wrong,your type doesnt exist.\n"); 
					rs.close(); 
					stmt.close();
					return "";
				}else {
					rs.close(); 
					stmt.close();
					return "employee";
				}
			}else {
				rs.close(); 
				stmt.close();
				return "customer";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	String registerAddress(String aptNumber,String streetNum,String streetName,String city,String province,String country,String postal){
		String addressID = getNextIndex("hotel.address","address_id");
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			stmt.executeUpdate("INSERT INTO hotel.address(address_id, apt_number,street_number,street_name,city,province,country,postal_code) \r\n"
					+ "VALUES ("+addressID+","+aptNumber+","+streetNum+",\'"+streetName+"\',\'"+city+"\',\'"+province+"\',\'"+country+"\',\'"+postal+"\');"); 
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addressID;
	}
	void registerUser(String hotel_ID, String firstName, String lastName, String address, String sin,
			String username, String password){
		String personID = getNextIndex("hotel.person","person_id");
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			stmt.executeUpdate("INSERT INTO hotel.person(person_id,hotel_id, first_name, last_name, address_id, sin, username, password) \r\n"
					+ "VALUES ("+personID+","+hotel_ID+ ",\'" +firstName+ "\',\'" +lastName+ "\'," +address+ "," +sin+ ",\'" +username+ "\',\'" +password+"\');"); 
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	String getNextIndex(String table, String idName) {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("	SELECT  "+idName+"\r\n"
					+ "	FROM    (\r\n"
					+ "	        SELECT  1 AS "+idName+"\r\n"
					+ "	        ) q1\r\n"
					+ "	WHERE   NOT EXISTS\r\n"
					+ "	        (\r\n"
					+ "	        SELECT  1\r\n"
					+ "	        FROM    "+table+"\r\n"
					+ "	        WHERE   "+idName+" = 1\r\n"
					+ "	        )\r\n"
					+ "	UNION ALL\r\n"
					+ "	SELECT  *\r\n"
					+ "	FROM    (\r\n"
					+ "	        SELECT  "+idName+" + 1\r\n"
					+ "	        FROM    "+table+" t\r\n"
					+ "	        WHERE   NOT EXISTS\r\n"
					+ "	                (\r\n"
					+ "	                SELECT  1\r\n"
					+ "	                FROM    "+table+" ti\r\n"
					+ "	                WHERE   ti."+idName+" = t."+idName+" + 1\r\n"
					+ "	                )\r\n"
					+ "	        ORDER BY\r\n"
					+ "	        	"+idName+"\r\n"
					+ "	        LIMIT 1\r\n"
					+ "	        ) q2\r\n"
					+ "	ORDER BY\r\n"
					+ "		"+idName+"\r\n"
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
	boolean validateHotelID(String hotelID) {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("SELECT hotel_id FROM hotel.hotel");
			while (rs.next()) {
				if(rs.getString(1).equals(hotelID)) {
					rs.close(); 
					stmt.close();
					return true;
				}
			} 
			rs.close(); 
			stmt.close();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	boolean validatePassword(String username, String password) {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("SELECT password FROM hotel.person where username = \'"+username+"\';");
			while (rs.next()) {
				if(rs.getString(1).equals(password)) {
					rs.close(); 
					stmt.close();
					return true;
				}
			} 
			rs.close(); 
			stmt.close();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.print("Something went wrong with the password, please try again\n"); 
		return false;
	}


}
