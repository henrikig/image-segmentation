package Utilities;

public class Parameters {

    public final static String P1 = "86016";
    public final static String P2 = "custom";
    public final static String CURRENT_IMAGE = P2;
    public final static String TEST_IMAGE = "./training_images/" + CURRENT_IMAGE + "/Test image.jpg";
    public final static String EVALUATED_SOLUTION = "../evaluator/Student_Segmentation_Files/" + CURRENT_IMAGE + "-";
    public final static String COLOR_SOLUTION = "./solution_images/" + CURRENT_IMAGE + "-COLOR-";
    public final static String BW_SOLUTION = "./solution_images/" + CURRENT_IMAGE + "-BW-";

    public final static int MIN_SEGMENTS = 10;
    public final static int MAX_SEGMENTS = 50;

    public final static int POPULATION_SIZE = 50;
    public final static int GENERATIONS = 100;
    public final static double MUTATION_PROB = 0.2;
    public final static double XOVER_PROB = 0.7;
}
