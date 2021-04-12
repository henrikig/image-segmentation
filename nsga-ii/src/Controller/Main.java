package Controller;

import Models.*;
import Utilities.ImageReader;
import Utilities.ImageWriter;
import Utilities.Parameters;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageReader.readImage();
        Vertex[][] initVertices = ImageReader.getImageAsGraph(image);

        Random random = new Random();

        Prim prim = new Prim(initVertices);

        List<Edge> path1 = prim.createMinimumSpanningTree();
        List<Edge> path2 = prim.createMinimumSpanningTree();

        int s1 = Parameters.MIN_SEGMENTS + random.nextInt(Parameters.MAX_SEGMENTS - Parameters.MIN_SEGMENTS);
        int s2 = Parameters.MIN_SEGMENTS + random.nextInt(Parameters.MAX_SEGMENTS - Parameters.MIN_SEGMENTS);
        Chromosome c1 = new Chromosome(path1, image.getHeight(), image.getWidth(), s1);
        Chromosome c2 = new Chromosome(path2, image.getHeight(), image.getWidth(), s2);
        Chromosome c3 = new Chromosome(c1, c2);

        ImageWriter.writeBWImage(c3);
        ImageWriter.writeColorImage(c3);
    }
}
