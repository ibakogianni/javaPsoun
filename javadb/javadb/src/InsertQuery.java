import java.sql.*;
public class InsertQuery {
    public static void main(String[] args ){
        try(Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javaworld", "root", ""
        )){

            //insert via prepareStatement
            String query = "Insert Into city(Name, countryCode, District, Population)"+
                    "Values('Chania', 'GRC', 'Crete', 100000)";

            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.executeUpdate();
            ResultSet genKeys = stmt.getGeneratedKeys();
            genKeys.next();
            System.out.println("id= " + genKeys.getLong(1));
            stmt.close();

            //insert via Result set
            Statement stmt2 = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            ResultSet result = stmt2.executeQuery("Select * From city");
            result.moveToInsertRow();
            result.updateString("Name", "Rethymno");
            result.updateString("CountryCode", "GRC");
            result.updateString("District", "Crete");
            result.updateLong("Population", 5000L);
            result.insertRow();
            result.last();
            System.out.println("id= " + result.getInt("ID"));
            result.close();
            stmt2.close();

        }catch (SQLException e ){
            System.out.println(e);
        }
    }
}
