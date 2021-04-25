package Utilities;

public class Parameters {

    public final static String IMAGE1 = "86016";
    public final static String IMAGE2 = "118035";
    public final static String IMAGE3 = "147091";
    public final static String IMAGE4 = "176035";
    public final static String IMAGE5 = "176039";
    public final static String IMAGE6 = "353013";

    public final static String CURRENT_IMAGE = IMAGE1;
    public final static String TEST_IMAGE = "./training_images/" + CURRENT_IMAGE + "/Test image.jpg";
    public final static String EVALUATION_FOLDER = "../evaluator/Student_Segmentation_Files/";
    public final static String SOLUTION_FOLDER = "./solution_images/";
    public final static String TXT_SOLUTION = "./txt_solutions/solution.txt";

    public final static int POPULATION_SIZE = 40;
    public final static int GENERATIONS = 100;
    public final static double MUTATE_CHROMOSOME = 0.2;
    public final static double MUTATION_PROB = 0.0001;
    public final static double XOVER_PROB = 0.9;

    public final static boolean MOEA = true;
    public final static int ELITISM = 4;
    public final static double W1 = 1.0;
    public final static double W2 = 4.0;
    public final static double W3 = 2.0;
}
