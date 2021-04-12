package Utilities;

import Models.Chromosome;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriter {

    public static void writeBWImage(Chromosome solution) throws IOException {
        File sol_f = new File(Parameters.COLOR_SOLUTION + solution.getSegments() + ".jpg");
        File eval_f = new File(Parameters.EVALUATED_SOLUTION + solution.getSegments() + ".jpg");

        int width = solution.getWidth();
        int height = solution.getHeight();

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = img.createGraphics();

        graphics.setPaint(Color.WHITE);
        graphics.fillRect(0,0, width, height);

        //TODO: Draw actual segments
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (solution.getDirection(x,y) == 3) {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }

        ImageIO.write(img, "jpg", sol_f);
        ImageIO.write(img, "jpg", eval_f);
        System.out.println("Image saved.");
    }

    public static void writeColorImage(Chromosome solution) throws IOException {
        File f = new File(Parameters.BW_SOLUTION + solution.getSegments() + ".jpg");

        BufferedImage img = ImageIO.read(new File(Parameters.TEST_IMAGE));

        int width = img.getWidth();
        int height = img.getHeight();

        //TODO: Draw actual segments
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (solution.getDirection(x,y) == 3) {
                    img.setRGB(x, y, Color.GREEN.getRGB());
                }
            }
        }

        ImageIO.write(img, "jpg", f);
        System.out.println("Image saved.");
    }
}
