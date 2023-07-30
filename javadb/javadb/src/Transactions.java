import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transactions {
    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javaworld", "root", ""
        )){
            conn.setAutoCommit(false);
            try{
                String[] cities = {"Chania", "Rethymno"};
                int[] inc = {10000, 5000};
                //update city
                PreparedStatement stmtCity = conn.prepareStatement("UPDATE city SET Population = Population + ? WHERE Name = ?");
                for(int i=0; i<cities.length; i++ ){
                    stmtCity.setInt(1, inc[i]);
                    stmtCity.setString(2, cities[i]);
                    stmtCity.executeUpdate();
                }
                stmtCity.close();
                int sum = 0;
                for(var v:inc){
                    sum +=v;
                }
                //update country
                PreparedStatement stmtCountry = conn.prepareStatement(
                        "UPDATE country SET Population = Population + ? WHERE name = 'Greece'"
                );
                stmtCountry.setInt(1, sum);
                stmtCountry.executeUpdate();
                stmtCountry.close();

                conn.commit();
                System.out.println("Transaction complete");
            }catch (SQLException e){
                System.out.println("Transaction Failes");
                conn.rollback();
            }
        }catch(SQLException e){
           e.printStackTrace();
        }
    }
}
