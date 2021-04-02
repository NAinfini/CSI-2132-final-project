import java.sql.*;
public class accessDataBase {
	private static accessDataBase dataBase = null;
	private Statement stmt;
	
	private accessDataBase(){
	}
	public static accessDataBase getInstance()
    {
        if (dataBase == null)
        	dataBase = new accessDataBase();
  
        return dataBase;
    }
	public String getPerson(){
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.print("JDBC driver not found "); 
		}
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("SELECT * FROM hotel.person");
			while (rs.next()) {
				System.out.print("Column 1 returned: "); 
				System.out.println(rs.getString(1)); 
			} 
			rs.close(); 
			stmt.close();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	boolean validateUserName(String userName) {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("SELECT username FROM hotel.person");
			while (rs.next()) {
				if(rs.equals(userName)) {
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
	boolean getUserType(String userInput) {
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/group_b04_g07","msui005","Z4321zxeZ4321zxe")){
			stmt = conn.createStatement();	
			ResultSet rs;
			rs = stmt.executeQuery("SELECT userID FROM hotel.person");
			while (rs.next()) {
				if(rs.equals(userInput)) {
					rs = stmt.executeQuery("IF EXISTS (SELECT userID FROM h WHERE TABLE_NAME = N'employee_ids')");
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
}
