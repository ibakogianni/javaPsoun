import java.sql.*;
public class Main {
    public static void main(String[] args) {
        try(Connection conn= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javaworld","root", "")
        ){
            System.out.println(conn);
        }catch(SQLException e){
            System.out.println(e);
        }

    }
}