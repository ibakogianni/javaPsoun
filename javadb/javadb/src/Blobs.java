import java.io.*;
import java.sql.*;

public class Blobs {
    public static byte[] combineImageBytes(byte[] image1, byte[] image2) {
        int totalLength = image1.length + image2.length;
        byte[] combinedImage = new byte[totalLength];

        System.arraycopy(image1, 0, combinedImage, 0, image1.length);
        System.arraycopy(image2, 0, combinedImage, image1.length, image2.length);

        return combinedImage;
    }
    public static void main(String[] args){

        try(Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javaworld", "root", ""
        )){

            File f = new File("batman.png");
            File f2 = new File("spiderman.png");
            File f3 = new File("Generated_image.png");
            byte[] image;
            byte[] image2;
            byte[] gen;
            try(BufferedInputStream bs = new BufferedInputStream(
                    new FileInputStream(f) ); BufferedInputStream bs2 = new BufferedInputStream( new FileInputStream(f2));  BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(f3))){

                //1. Read The Image (from file)
                image = bs.readAllBytes();
                image2 = bs2.readAllBytes();
                //2. write the image to db
                String query = "Insert INTO images(image, descr)" + "VALUES(?, ?)";


                PreparedStatement stmt =conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);


                //stmt.setBlob(1, new SerialBlob(image));
                stmt.setBytes(1, image);
                stmt.setString(2, "My sql logo");



                stmt.executeUpdate();

                ResultSet genKeys = stmt.getGeneratedKeys();
                genKeys.next();
                long id = genKeys.getLong(1);
                System.out.println("id=" +id);
                stmt.close();
////2
String query2 = "Insert INTO images(image2, descr)" + "VALUES(?, ?)";
                PreparedStatement stmt2 =conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);
                stmt2.setBytes(1, image2);
                stmt2.setString(2, "My sql logo");

                stmt2.executeUpdate();

                ResultSet genKeys2 = stmt2.getGeneratedKeys();
                genKeys2.next();
                long id2 = genKeys2.getLong(1);
                System.out.println("id=" +id);
                stmt2.close();
                //3. read image from db
                Statement stmtRead = conn.createStatement();
                ResultSet result = stmtRead.executeQuery("SELECT * From images " + "WHERE image_id = " +id);
                result.next();
                System.out.println(result.getString("descr"));
                image = result.getBytes("image");


                //2
                Statement stmtRead2 = conn.createStatement();
                ResultSet result2 = stmtRead2.executeQuery("SELECT * From images " + "WHERE image_id = " +id2);
                result2.next();
                System.out.println(result.getString("descr"));
                image2 = result.getBytes("image");
                byte[] combined =  combineImageBytes(image, image2);
                //4. save to file
                bos.write(combined);
            }catch(FileNotFoundException e){
                System.err.println("file Not found");
            }catch (IOException e){
                System.err.println("I/O error");
            }

        }catch (SQLException e){
            System.out.println(e);
        }

    }
}
