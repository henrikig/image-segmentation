package Controller;

import Models.Chromosome;
import Utilities.ImageWriter;
import Utilities.Utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class Main {

    public static void main(String[] args) throws IOException {

        Utils.cleanUpDirectories();

        NSGA nsga = new NSGA();

        nsga.main();

    }
}
