package Utilities;

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
}
