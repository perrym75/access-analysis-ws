package com.infosec.accessanalysis.algorithm;

import java.util.*;

public class Subsets {
    private static <T> void calculate(Set<T> input, Set<Set<T>> result) {
        if (!input.isEmpty()) {
            result.add(input);
            for (T item : input)
            {
                Set<T> exceptOne = new HashSet<>(input);
                exceptOne.remove(item);
                calculate(exceptOne, result);
            }
        }
    }

    public static <T> Set<Set<T>> getSubsets(Set<T> input) {
        Set<Set<T>> result = new HashSet<>();
        calculate(input, result);
        return result;
    }
}
