package Utilities;

import Models.Chromosome;
import Models.Segments;
import Models.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageWriter {

    public static void writeBWImage(Chromosome chromosome, Vertex[][] vertexGrid) throws IOException {
        File solutionFile = new File(Parameters.COLOR_SOLUTION + chromosome.getNumSegments() + ".jpg");
        File evaluationFile = new File(Parameters.EVALUATED_SOLUTION + chromosome.getNumSegments() + ".jpg");

        Vertex[][] solution = chromosome.createVertexGrid(vertexGrid);

        int height = solution.length;
        int width = solution[0].length;

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Graphics2D graphics = img.createGraphics();

        graphics.setPaint(Color.BLACK);
        graphics.fillRect(0,0, width, height);

        graphics.setPaint(Color.WHITE);
        graphics.fillRect(1,1, width-2, height-2);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (solution[y][x].getIsEdge()) {
                    img.setRGB(x, y, Color.BLACK.getRGB());
                }
            }
        }



        ImageIO.write(img, "jpg", solutionFile);
        ImageIO.write(img, "jpg", evaluationFile);
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
