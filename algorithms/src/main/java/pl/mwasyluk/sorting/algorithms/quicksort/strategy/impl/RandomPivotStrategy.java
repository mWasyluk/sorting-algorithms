package pl.mwasyluk.sorting.algorithms.quicksort.strategy.impl;

import java.util.Random;

import pl.mwasyluk.sorting.algorithms.quicksort.strategy.QuickSortStrategy;

public class RandomPivotStrategy implements QuickSortStrategy {
    private Random random = new Random();

    @Override
    public int selectPivotIndex(int[] tab, int low, int high) {
        int randomIndex = low + random.nextInt(high - low + 1);
        swap(tab, randomIndex, high);
        return high;
    }
}
