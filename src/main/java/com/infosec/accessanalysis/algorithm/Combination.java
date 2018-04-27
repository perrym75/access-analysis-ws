package com.infosec.accessanalysis.algorithm;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Combination<T> {
    private Set<List<T>> result;

    private void calculate(List<T> list) {
        if (!list.isEmpty()) {
            result.add(list);
            for (int i = 0; i < list.size(); i++)
            {
                int index = i;
                List<T> exceptOne = new LinkedList<>(
                        IntStream.range(0, list.size())
                                .filter(n -> n != index)
                                .mapToObj(list::get)
                                .collect(Collectors.toList()));
                calculate(exceptOne);
            }
        }
    }

    public Set<List<T>> getCombinations(List<T> input) {
        result = new HashSet<>();
        calculate(input);
        return result;
    }

    private void printList(List<T> list) {
        list.forEach(e -> System.out.print(e.toString() + ","));
        System.out.println();
    }

    public void print() {
        result.forEach(this::printList);
    }
}
