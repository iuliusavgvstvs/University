package utils.comparators;

import domain.Raport;

import java.util.Comparator;

public class AverageGradeComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Raport r1 = (Raport) o1;
        Raport r2 = (Raport) o2;
        return Double.compare(r1.getAverage(), r2.getAverage());
    }
}
