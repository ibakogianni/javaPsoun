import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DATETIME {
    public static void main(String[] args){
        try(Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javaworld", "root", ""
        )){
            /* 1. INSERT */
            String query = "INSERT INTO dates (vdate, vtime, vdatetime) VALUES (?,?,?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            //initialize the object
            LocalDate d = LocalDate.of(2021, Month.APRIL, 3);
            //LocalDate d = LocalDate.now();
            LocalTime t = LocalTime.of(11, 12,13);
            //LocalTime t = LocalTime.now();
            LocalDateTime dt = LocalDateTime.now();
            //LocalDateTime dt = LocalDateTime.of(2021, Month.APRIL, 3, 11, 12, 13);
            //LocalDateTime dt = LocalDateTime.of(d, t);

            //Prepare the statement
            stmt.setDate(1, java.sql.Date.valueOf(d));
            stmt.setTime(2, java.sql.Time.valueOf(t));
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(dt));
            stmt.executeUpdate();
            stmt.close();

            /*Read*/
            Statement stmt2 = conn.createStatement();

            ResultSet result = stmt2.executeQuery("SELECT * From dates");

            //traverse and convert java.sql datatypes of to java local* datatypes
            result.next();
            java.sql.Date vd = result.getDate("vdate");
            LocalDate ld = vd.toLocalDate();
            java.sql.Time vt = result.getTime("vtime");
            LocalTime lt = vt.toLocalTime();
            java.sql.Timestamp vdt = result.getTimestamp("vdatetime");
            LocalDateTime ldt = vdt.toLocalDateTime();
            java.sql.Timestamp vtt = result.getTimestamp("vtimestamp");
            LocalDateTime ltt = vtt.toLocalDateTime();
            // and make some custom formating
            String sdate = ld.format(DateTimeFormatter.ofPattern("dd/MM/YYYY"));
            String stime = lt.format(DateTimeFormatter.ofPattern("hh:mm:ss"));
            String sdatetime = ldt.format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss"));

            System.out.println(sdate);
            System.out.println(stime);
            System.out.println(sdatetime);
            System.out.println(ltt);

            stmt.close();
            result.close();


        }catch (SQLException e){
            System.out.println(e);
        }
    }

}
