import java.sql.*;

public class Metadata {
    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javaworld", "root", "" )
        ){
            DatabaseMetaData metadata = conn.getMetaData();
            //DMBS + driver info

            System.out.println("DBMS name: " + metadata.getDatabaseProductName());
            System.out.println("DBMS version: " + metadata.getDatabaseProductVersion());
            System.out.println("DBMS major version: " + metadata.getDatabaseMajorVersion());
            System.out.println("DBMS minor version: " + metadata.getDatabaseMinorVersion());

            //DB Tables
            ResultSet resultSet = metadata.getTables(null, null, null, null);
            //or metadata.getTables(null, null, null, new String[], {"TABLE"});
            System.out.println("\n\nTables: ");
            while (resultSet.next()){
                System.out.println();
                for(int i=1; i<=4; i++){
                    System.out.print(resultSet.getString(i) + " ");
                }
            }

            //table collumns
            ResultSet columns = metadata.getColumns(null, null, "country", null);
            System.out.println("\n\nTable columns: ");
            while(columns.next()){
                System.out.print("Column: " + columns.getString("COLUMN_NAME") +
                        " " +  columns.getString("DATA_TYPE") +" " +
                        "NULL " + columns.getString("IS_NULLABLE") + " " +
                        "AUTOINC " + columns.getString("IS_AUTOINCREMENT") + "\n");
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
