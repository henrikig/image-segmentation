package Utilities;

import Models.Vertex;

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

    public static Vertex[][] getImageAsGraph(BufferedImage img) {
        int width = img.getWidth();
        int height = img.getHeight();

        Vertex[][] vertices = new Vertex[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                int pixel = img.getRGB(x, y);
                int r = (pixel >> 16) & 0x000000FF;
                int g = (pixel >> 8) & 0x000000FF;
                int b = (pixel) & 0x000000FF;

                vertices[y][x] = new Vertex(x, y, new int[]{r, g, b});
            }
        }

        return vertices;
    }

    public static void main(String[] args) throws IOException {

        BufferedImage image = readImage();

        Vertex[][] graph = getImageAsGraph(image);

        for (Vertex[] ls : graph) {
            System.out.println(Arrays.deepToString(ls));
        }

        System.out.println(image.getHeight());
        System.out.println(image.getWidth());

    }
}
