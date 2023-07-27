import java.sql.*;

public class StroreProcedures {

    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javaworld","root", ""
        )){

            //Insert via Result set
            CallableStatement stmt = conn.prepareCall("CALL country_cities(?)");
            stmt.setString(1, "Greece");

            ResultSet result = stmt.executeQuery();
            while(result.next()){
                System.out.println(result.getString("Name")+ " "
                        + result.getString("District")+
                        " " + result.getLong("Population"));
            }
            result.close();
            stmt.close();

        }catch (SQLException e){
            System.out.println(e);
        }
    }
}
