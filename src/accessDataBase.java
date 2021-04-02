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
				if(rs.toString().equals(userName)) {
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
		System.out.print("Something went wrong, please try again"); 
		return false;
	 }
	String getUserType(String userInput) {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("select person_id from customer where exists(SELECT userID FROM hotel.person where username = "+ userInput+")");
			if(rs.toString().isBlank()) {
				rs = stmt.executeQuery("select person_id from employee where exists(SELECT userID FROM hotel.person where username = "+ userInput+")");
				if(rs.toString().isBlank()) {
					System.out.print("Something went wrong."); 
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
		System.out.print("Something went wrong, please try again"); 
		return "";
	}

	String registerAddress(String aptNumber,String streetNum,String streetName,String city,String province,String country,String postal){
		return "";
	}
	public void registerUser(String hotel_ID, String firstName, String lastName, String Address, String sin,
			String username, String password) {
		
	}
}
