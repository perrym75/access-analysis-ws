package com.infosec.accessanalysis.algorithm;

import java.util.*;

public class Subsets {
    public static <T> Set<Set<T>> getSubsets(Set<T> input) {
        Set<Set<T>> result = new HashSet<>();
        Set<T> empty = new HashSet<>();

        result.add(empty);

        for (T inputItem : input) {
            Set<Set<T>> subsetTemp = new HashSet<>();
            for (Set<T> subsetItem : result) {
                subsetTemp.add(new HashSet<>(subsetItem));
            }

            for (Set<T> subsetItem : subsetTemp) {
                subsetItem.add(inputItem);
            }

            result.addAll(subsetTemp);
        }

        return result;
    }
}
