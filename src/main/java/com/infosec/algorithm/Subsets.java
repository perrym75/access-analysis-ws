package com.infosec.algorithm;

import java.util.*;

public class Subsets {
    public static <T> Collection<Collection<T>> getSubsets(Collection<T> input) {
        Collection<Collection<T>> result = new ArrayList<>(1 << input.size());
        Collection<T> empty = new ArrayList<>();

        result.add(empty);

        for (T inputItem : input) {
            Collection<Collection<T>> subsetTemp = new ArrayList<>();
            for (Collection<T> subsetItem : result) {
                subsetTemp.add(new ArrayList<>(subsetItem));
            }

            for (Collection<T> subsetItem : subsetTemp) {
                subsetItem.add(inputItem);
            }

            result.addAll(subsetTemp);
        }

        return result;
    }
}
