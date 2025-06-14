package pl.mwasyluk.sorting.algorithms.quicksort;

import pl.mwasyluk.sorting.algorithms.SortingAlgorithm;
import pl.mwasyluk.sorting.algorithms.quicksort.strategy.QuickSortStrategy;
import pl.mwasyluk.sorting.algorithms.quicksort.strategy.impl.HighestPivotStrategy;

public class QuickSort extends SortingAlgorithm {
    private QuickSortStrategy strategy;

    public QuickSort() {
        this(new HighestPivotStrategy());
    }

    public QuickSort(QuickSortStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("QuickSortStrategy cannot be null.");
        }
        this.strategy = strategy;
    }

    public QuickSortStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(QuickSortStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    protected void execute_sorting(int[] tab) {
        sortRange(tab, 0, tab.length - 1);
    }
    
    private void sortRange(int[] tab, int low, int high) {
        if (low >= high) {
            return;
        }

        int pivotIndex = partition(tab, low, high);

        sortRange(tab, low, pivotIndex - 1);
        sortRange(tab, pivotIndex + 1, high);
    }

    private int partition(int[] tab, int low, int high) {
        int pivotIndex = strategy.selectPivotIndex(tab, low, high);

        int k = low - 1;
        for (int j = low; j < high; j++) {
            if (tab[j] <= tab[pivotIndex]) {
                strategy.swap(tab, j, ++k);
            }
        }

        strategy.swap(tab, pivotIndex, ++k);
        return k;
    }
}
