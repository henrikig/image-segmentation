package Controller;

import Models.*;
import Utilities.ImageReader;
import Utilities.ImageWriter;
import Utilities.Parameters;

import javax.swing.text.Segment;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
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

        Chromosome c1 = new Chromosome(path1, image.getHeight(), image.getWidth(), 3);
        Chromosome c2 = new Chromosome(path2, image.getHeight(), image.getWidth(), 3);
        Chromosome c3 = new Chromosome(c1, c2);


        for (int[] genes : c1.getGenotype()) {
            System.out.println(Arrays.toString(genes));
        }

        Segments s = new Segments(c1.getGenotype());
        int[][] segments = s.makeSegments();
        System.out.println();

        for (int[] seg : segments) {
            System.out.println(Arrays.toString(seg));
        }

        ImageWriter.writeBWImage(s, initVertices);
    }
}
