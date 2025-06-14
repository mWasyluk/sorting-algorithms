package pl.mwasyluk.sorting.algorithms.quicksort.strategy.impl;

import pl.mwasyluk.sorting.algorithms.quicksort.strategy.QuickSortStrategy;

public class MedianPivotStrategy implements QuickSortStrategy {
    @Override
    public int selectPivotIndex(int[] tab, int low, int high) {
        int pivotIndex = getMedianOfThreeIndex(tab, low, high);
        swap(tab, pivotIndex, high);
        return high;
    }

    private int getMedianOfThreeIndex(int[] tab, int low, int high) {
        int mid = (low + high) / 2;
        int a = tab[low], b = tab[mid], c = tab[high];
        
        if (a < b) {
            if (b < c) {
                return mid; // a < b < c ⇒ b
            } else if (a < c) {
                return high; // a < c ≤ b ⇒ c
            } else {
                return low; // c ≤ a < b ⇒ a
            }
        } else { // b ≤ a
            if (a < c) {
                return low; // b ≤ a < c ⇒ a
            } else if (b < c) {
                return high; // b < c ≤ a ⇒ c
            } else {
                return mid; // c ≤ b ≤ a ⇒ b
            }
        }
    }
}
