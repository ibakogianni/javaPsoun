import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

class ImageCombiner {
    public static void main(String[] args) throws IOException {
        // Assuming you have two byte arrays named "image1Bytes" and "image2Bytes"
        // representing the image
        File f = new File("batman.png");
        File f2 = new File("spiderman.png");
        File f3 = new File("Generated_image.png");
        BufferedInputStream bs = new BufferedInputStream(
                new FileInputStream(f) );
        BufferedInputStream bs2 = new BufferedInputStream( new FileInputStream(f2));
        byte[] image1Bytes = bs.readAllBytes();
        byte[] image2Bytes = bs2.readAllBytes();


        try {
            BufferedImage image1 = ImageIO.read(new ByteArrayInputStream(image1Bytes));
            BufferedImage image2 = ImageIO.read(new ByteArrayInputStream(image2Bytes));

            BufferedImage combinedImage = combineImages(image1, image2);

            // Save the combined image as a new image file
            File outputFile = new File("combined_image.png");
            ImageIO.write(combinedImage, "png", outputFile);

            System.out.println("Combined image saved successfully.");
        } catch (IOException e) {
            System.err.println("Error while processing images: " + e.getMessage());
        }
    }

    public static BufferedImage combineImages(BufferedImage image1, BufferedImage image2) {
        // Get the maximum width and height of the two images
        int combinedWidth = Math.max(image1.getWidth(), image2.getWidth());
        int combinedHeight = Math.max(image1.getHeight(), image2.getHeight());

        // Create a new blank image to combine the two images
        BufferedImage combinedImage = new BufferedImage(combinedWidth, combinedHeight, BufferedImage.TYPE_INT_ARGB);

        // Draw the first image onto the combined image
        combinedImage.getGraphics().drawImage(image1, 0, 0, null);

        // Draw the second image onto the combined image
        combinedImage.getGraphics().drawImage(image2, 0, 0, null);

        return combinedImage;
    }
}
