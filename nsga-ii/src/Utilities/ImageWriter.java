package Utilities;

import Models.Chromosome;
import Models.Segments;
import Models.Vertex;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class ImageWriter {

    private static final AtomicInteger count = new AtomicInteger(0);

    public static void writeBWImage(Chromosome chromosome, Vertex[][] vertexGrid) throws IOException {
        int id = count.incrementAndGet();
        File solutionFile = new File(Parameters.BW_SOLUTION + chromosome.getNumSegments() + "#" + id + ".jpg");
        File evaluationFile = new File(Parameters.EVALUATED_SOLUTION + chromosome.getNumSegments() + "#" + id + ".jpg");

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



        //ImageIO.write(img, "jpg", solutionFile);
        ImageIO.write(img, "jpg", evaluationFile);
    }

    public static void writeColorImage(Chromosome chromosome, Vertex[][] vertexGrid) throws IOException {
        File solutionFile = new File(Parameters.COLOR_SOLUTION + chromosome.getNumSegments() + ".jpg");

        Vertex[][] solution = chromosome.createVertexGrid(vertexGrid);

        int height = solution.length;
        int width = solution[0].length;

        BufferedImage img = ImageIO.read(new File(Parameters.TEST_IMAGE));

        for (int x = 0; x < width; x++) {
            img.setRGB(x, 0, Color.GREEN.getRGB());
        }

        for (int x = 0; x < width; x++) {
            img.setRGB(x, height-1, Color.GREEN.getRGB());
        }

        for (int y = 0; y < height; y++) {
            img.setRGB(0, y, Color.GREEN.getRGB());
        }

        for (int y = 0; y < height; y++) {
            img.setRGB(width-1, y, Color.GREEN.getRGB());
        }

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (solution[y][x].getIsEdge()) {
                    img.setRGB(x, y, Color.GREEN.getRGB());
                }
            }
        }

        ImageIO.write(img, "jpg", solutionFile);
    }
}
