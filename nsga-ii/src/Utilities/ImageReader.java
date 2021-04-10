package Utilities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageReader {

    public static BufferedImage readImage() throws IOException {
        BufferedImage img = ImageIO.read(new File(Parameters.TEST_IMAGE));

        return img;
    }

    public static int[][] getFlattenedImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        int[][] flattenedImageInt = new int[width*height][3];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {

                int pixel = img.getRGB(x, y);
                int r = (pixel >> 16) & 0x000000FF;
                int g = (pixel >> 8) & 0x000000FF;
                int b = (pixel) & 0x000000FF;

                flattenedImageInt[x + y * width][0] = r;
                flattenedImageInt[x + y * width][1] = g;
                flattenedImageInt[x + y * width][2] = b;
            }
        }

        return flattenedImageInt;
    }

    public static int[][][] get2DImage(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        int[][][] image2D = new int[height][width][3];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int pixel = img.getRGB(x, y);
                int r = (pixel >> 16) & 0x000000FF;
                int g = (pixel >> 8) & 0x000000FF;
                int b = (pixel) & 0x000000FF;

                image2D[y][x][0] = r;
                image2D[y][x][1] = g;
                image2D[y][x][2] = b;
            }
        }

        return image2D;
    }



    public static void saveImage(BufferedImage img, String filename) throws IOException {
        File f = new File(filename);
        ImageIO.write(img, "jpg", f);
        System.out.println("Image saved.");
    }

    public static void main(String[] args) throws IOException {

        BufferedImage image = readImage();

        int[][][] image2D = get2DImage(image);

        for (int[][] ls : image2D) {
            System.out.println(Arrays.deepToString(ls));
        }

        System.out.println(image.getHeight());
        System.out.println(image.getWidth());

    }
}
