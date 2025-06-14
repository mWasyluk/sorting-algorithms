package pl.mwasyluk.sorting.algorithms.quicksort.strategy.impl;

import pl.mwasyluk.sorting.algorithms.quicksort.strategy.QuickSortStrategy;

public class HighestPivotStrategy implements QuickSortStrategy {
    @Override
    public int selectPivotIndex(int[] tab, int low, int high) {
        return high;
    }

}
