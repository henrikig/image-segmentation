package Controller;

import Utilities.Parameters;
import Utilities.Utils;

import java.io.IOException;


public class Main {

    public static void main(String[] args) throws IOException {

        Utils.cleanUpDirectories();

        GeneralSolver solver;

        if (Parameters.MOEA) {

            solver = new NSGA();

        } else {

            solver = new GA();

        }

        solver.main();

    }
}
