package Utilities;

public class Parameters {

    public final static String P1 = "86016";
    public final static String P2 = "c2";
    public final static String P3 = "custom";
    public final static String P4 = "rgb";
    public final static String CURRENT_IMAGE = P1;
    public final static String TEST_IMAGE = "./training_images/" + CURRENT_IMAGE + "/Test image.jpg";
    public final static String EVALUATION_FOLDER = "../evaluator/Student_Segmentation_Files/";
    public final static String SOLUTION_FOLDER = "./solution_images/";
    public final static String EVALUATED_SOLUTION = EVALUATION_FOLDER + CURRENT_IMAGE + "-";
    public final static String COLOR_SOLUTION = SOLUTION_FOLDER + CURRENT_IMAGE + "-COLOR-";
    public final static String BW_SOLUTION = SOLUTION_FOLDER + CURRENT_IMAGE + "-BW-";

    public final static int POPULATION_SIZE = 40;
    public final static int GENERATIONS = 100;
    public final static double MUTATE_CHROMOSOME = 0.2;
    public final static double MUTATION_PROB = 0.0001;
    public final static double XOVER_PROB = 0.9;
}
