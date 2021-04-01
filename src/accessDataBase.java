import java.sql.*;
public class accessDataBase {
	private Statement stmt;
	accessDataBase(){
		try (Connection conn = DriverManager.getConnection(
		               "jdbc:postgresql://web0.site.uottawa.ca:15432/msui005 ",
		               "msui005", "Z4321zxe");
		){
			stmt = conn.createStatement();
			 
		 
		 } catch(SQLException ex) {
		   ex.printStackTrace();
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
