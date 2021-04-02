import java.sql.*;
public class accessDataBase {
	private Statement stmt;
	
	
	
	accessDataBase(){
		try {
			Class.forName("org.postgresql.Driver");
		}catch(ClassNotFoundException e) {
			System.out.print("JDBC driver not found "); 
		}
		try(Connection conn = DriverManager.getConnection("jdbc:postgresql://web0.site.uottawa.ca:15432/msui005","msui005","Z4321zxe")){
			stmt = conn.createStatement();			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getPerson(){
		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT * FROM hotel.person");
			while (rs.next()) {
				System.out.print("Column 1 returned: "); 
				System.out.println(rs.getString(1)); 
			} 
			rs.close(); 
			stmt.close();
			return "";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
