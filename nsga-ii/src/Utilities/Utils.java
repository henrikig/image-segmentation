package Utilities;

import Models.Direction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Utils {

    public static double getEuclideanDist(int[] rgb1, int[] rgb2) {
        double rDist = rgb1[0] - rgb2[0];
        double gDist = rgb1[1] - rgb2[1];
        double bDist = rgb1[2] - rgb2[2];


        return sqrt(pow(rDist, 2) + pow(gDist, 2) + pow(bDist, 2));
    }

    public static double getEuclideanDist(int[] rgb1, double[] rgb2) {
        double rDist = rgb1[0] - rgb2[0];
        double gDist = rgb1[1] - rgb2[1];
        double bDist = rgb1[2] - rgb2[2];


        return sqrt(pow(rDist, 2) + pow(gDist, 2) + pow(bDist, 2));
    }

    public static boolean checkDirection(Direction d, int x, int y, int height, int width) {
        return 0 <= x + d.getShiftX() && x + d.getShiftX() < width && 0 <= y + d.getShiftY() && y + d.getShiftY() < height;
    }

    public static void cleanUpDirectories() {

        File solDir = new File(Parameters.SOLUTION_FOLDER);
        File evalDir = new File(Parameters.EVALUATION_FOLDER);


        try {
            for (File f : solDir.listFiles()) {
                if (!f.isDirectory()) {
                    if (!f.delete()) {
                        System.out.printf("Could not delete file %s", f.toString());
                    }
                }
            }

            for (File f : evalDir.listFiles()) {
                if (!f.isDirectory()) {
                    if (!f.delete()) {
                        System.out.printf("Could not delete file %s", f.toString());
                    }
                }
            }

            System.out.println("Image directories cleared.\n");

        } catch (NullPointerException e) {
            System.out.println("Could not delete all files");
        }
    }

    public static void writeSolution(String solution) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(Parameters.TXT_SOLUTION));

        writer.write(solution);

        writer.close();
    }
}
