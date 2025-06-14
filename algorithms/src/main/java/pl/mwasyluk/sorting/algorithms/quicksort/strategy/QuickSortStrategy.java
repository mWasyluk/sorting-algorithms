package pl.mwasyluk.sorting.algorithms.quicksort.strategy;

public interface QuickSortStrategy {
    int selectPivotIndex(int[] tab, int low, int high);

    default void swap(int[] tab, int index1, int index2) {
        int tmpHigh = tab[index1];
        tab[index1] = tab[index2];
        tab[index2] = tmpHigh;
    }
}
