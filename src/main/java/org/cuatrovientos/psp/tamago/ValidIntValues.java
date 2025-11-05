package org.cuatrovientos.psp.tamago;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidIntValues {

    private final ArrayList<Integer> excluded = new ArrayList<>();

    private final int start;
    private final int stop;

    public ValidIntValues(int start, int stop) {
        this.start = start;
        this.stop = stop;
    }

    public boolean contains(int n) {
        if (n >= start && n <= stop) {
            return !excluded.contains(n);
        }
        return false;
    }

}
