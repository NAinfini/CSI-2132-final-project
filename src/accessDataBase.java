import java.nio.charset.StandardCharsets;
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
	//Creates only 1 instance of access to database per process
	public static accessDataBase getInstance()
    {
        if (dataBase == null)
        	dataBase = new accessDataBase();
  
        return dataBase;
    }
	//validates of the username is in the database
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
	//validates if the user is a customer or an employee
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
	//add address to the database
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
	
	//add current user to database, uses registerAddress
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
	
	//get next empty ID for dynamic allocation
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
	
	//check if the hotel id exists before inserting person to ensure no error
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
	
	//check if password matches username	
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

	//return all brands as a string
	String getBrands() {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			String result = "";
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("SELECT * FROM hotel.parenthotel");
			ResultSetMetaData rsMeta = rs.getMetaData();
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
                    String formatedValue = String.format(format, new String(rs.getBytes(i), StandardCharsets.UTF_8));
                    result += formatedValue + "| ";
                    i++;
                }
            }
            result += "\n";
            rs.close();
            stmt.close();
            return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	String getHotels(int brandID) {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			String result = "";
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("SELECT * FROM hotel.hotel where brand_id = \'"+brandID+"\'");
			ResultSetMetaData rsMeta = rs.getMetaData();
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
                    String formatedValue = String.format(format, new String(rs.getBytes(i), StandardCharsets.UTF_8));
                    result += formatedValue + "| ";
                    i++;
                }
            }
            rs.close();
            stmt.close();
            result += "\n";
            return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	String getRooms(int brandID, int hotelID) {
		// TODO Auto-generated method stub
		return null;
	}
	String getOneRoom(int brandID, int hotelID, int roomNum) {
		// TODO Auto-generated method stub
		return null;
	}
}
