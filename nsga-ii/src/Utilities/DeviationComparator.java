package Utilities;

import Models.Chromosome;

import java.util.Comparator;

public class DeviationComparator implements Comparator<Chromosome> {

    @Override
    public int compare(Chromosome o1, Chromosome o2) {
        if (o1.getDeviation() < o2.getDeviation()) {
            return -1;
        } else if (o1.getDeviation() > o2.getDeviation()) {
            return 1;
        }

        return 0;
    }
}
